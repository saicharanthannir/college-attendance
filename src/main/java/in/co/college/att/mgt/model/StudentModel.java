package in.co.college.att.mgt.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.college.att.mgt.bean.CourseBean;
import in.co.college.att.mgt.bean.StudentBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.exception.DatabaseException;
import in.co.college.att.mgt.exception.DuplicateRecordException;
import in.co.college.att.mgt.util.DataUtility;
import in.co.college.att.mgt.util.JDBCDataSource;

public class StudentModel {
	private static Logger log = Logger.getLogger(StudentModel.class);

    /**
     * Find next PK of Student
     * 
     * @throws DatabaseException
     *
     */
    public Integer nextPK() throws DatabaseException {
        log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT MAX(ID) FROM a_student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model nextPK End");
        return pk + 1;
    }
    
    public Integer nextRollNo() throws DatabaseException {
        log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT MAX(rollNo) FROM a_student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model nextPK End");
        return pk + 1;
    }

    /**
     * Add a Student
     * 
     * @param bean
     * @throws DatabaseException
     * @throws ApplicationException 
     */
    public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        // get College Name
       

        StudentBean duplicateName = findByEmailId(bean.getEmailId());
        int pk = 0;

        if (duplicateName != null) {
            throw new DuplicateRecordException("Email already exists");
        }
        
        
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            // Get auto-generated next primary key
            System.out.println(pk + " in ModelJDBC");
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO a_student VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setLong(2,nextRollNo());
            pstmt.setString(3, bean.getFirstName());
            pstmt.setString(4, bean.getLastName());
            pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(6,bean.getGender());
            pstmt.setString(7, bean.getMobileNo());
            pstmt.setLong(8, bean.getCourseId());
            pstmt.setString(9, bean.getCourseName());
            pstmt.setLong(10, 0);
            pstmt.setString(11, null);
            pstmt.setString(12,bean.getSemester());
            pstmt.setString(13, bean.getEmailId());
            pstmt.setString(14, bean.getFatherEmailId());
            pstmt.setString(15, bean.getFatherMobileNo());
            pstmt.setString(16, bean.getProfilePic());
            pstmt.setString(17, bean.getCreatedBy());
            pstmt.setString(18, bean.getModifiedBy());
            pstmt.setTimestamp(19, DataUtility.getCurrentTimestamp());
            pstmt.setTimestamp(20,DataUtility.getCurrentTimestamp());
            pstmt.setLong(21,bean.getUserId());
            pstmt.setString(22,bean.getStatus());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException(
                    "Exception : Exception in add Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a Student
     * 
     * @param bean
     * @throws DatabaseException
     *  @throws ApplicationException
     */
    public void delete(StudentBean bean) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM a_student WHERE ID=?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();

        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
            throw new ApplicationException(
                    "Exception : Exception in delete Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete Started");
    }

    /**
     * Find Student by Email
     * 
     * @param Email
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public StudentBean findByEmailId(String Email) throws ApplicationException {
        log.debug("Model findBy Email Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM a_student WHERE EMAILID=?");
        StudentBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, Email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getLong(2));
                bean.setFirstName(rs.getString(3));
                bean.setLastName(rs.getString(4));
                bean.setDob(rs.getDate(5));
                bean.setGender(rs.getString(6));
                bean.setMobileNo(rs.getString(7));
                bean.setCourseId(rs.getLong(8));
                bean.setCourseName(rs.getString(9));
                bean.setSubjectId(rs.getLong(10));
                bean.setSubjectName(rs.getString(11));
                bean.setSemester(rs.getString(12));
                bean.setEmailId(rs.getString(13));
                bean.setFatherEmailId(rs.getString(14));
                bean.setFatherMobileNo(rs.getString(15));
                bean.setProfilePic(rs.getString(16));
                bean.setCreatedBy(rs.getString(17));
                bean.setModifiedBy(rs.getString(18));
                bean.setCreatedDatetime(rs.getTimestamp(19));
                bean.setModifiedDatetime(rs.getTimestamp(20));
                bean.setUserId(rs.getLong(21));
                bean.setStatus(rs.getString(22));
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by Email");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findBy Email End");
        return bean;
    }

    /**
     * Find Student by PK
     * 
     * @param pk
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public StudentBean findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM a_student WHERE ID=?");
        StudentBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getLong(2));
                bean.setFirstName(rs.getString(3));
                bean.setLastName(rs.getString(4));
                bean.setDob(rs.getDate(5));
                bean.setGender(rs.getString(6));
                bean.setMobileNo(rs.getString(7));
                bean.setCourseId(rs.getLong(8));
                bean.setCourseName(rs.getString(9));
                bean.setSubjectId(rs.getLong(10));
                bean.setSubjectName(rs.getString(11));
                bean.setSemester(rs.getString(12));
                bean.setEmailId(rs.getString(13));
                bean.setFatherEmailId(rs.getString(14));
                bean.setFatherMobileNo(rs.getString(15));
                bean.setProfilePic(rs.getString(16));
                bean.setCreatedBy(rs.getString(17));
                bean.setModifiedBy(rs.getString(18));
                bean.setCreatedDatetime(rs.getTimestamp(19));
                bean.setModifiedDatetime(rs.getTimestamp(20));
                bean.setUserId(rs.getLong(21));
                bean.setStatus(rs.getString(22));
            }
            rs.close();
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return bean;
    }

    /**
     * Update a Student
     * 
     * @param bean
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public void update(StudentBean bean) throws ApplicationException,
            DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;

        StudentBean beanExist = findByEmailId(bean.getEmailId());

        // Check if updated Roll no already exist
        if (beanExist != null && beanExist.getId() != bean.getId()) {
            throw new DuplicateRecordException("Email Id is already exist");
        }

        // get College Name
        CourseModel cModel=new CourseModel();
        CourseBean cBean=cModel.findByPK(bean.getCourseId());
        bean.setCourseName(cBean.getName());

        try {

            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE a_student SET RollNo=?,FIRSTNAME=?,LASTNAME=?,DOB=?,GENDER=?,MOBILENO=?,CourseId=?,"
                    		+ "CourseName=?,SubjectId=?,SubjectName=?,Semester=?,EmailId=?,FatherEmailId=?,FatherMobileNo=?,ProfilePic=?,"
                    		+ "CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=?,userId=?,status=? WHERE ID=?");
            pstmt.setLong(1,bean.getRollNo());
            pstmt.setString(2, bean.getFirstName());
            pstmt.setString(3, bean.getLastName());
            pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(5,bean.getGender());
            pstmt.setString(6, bean.getMobileNo());
            pstmt.setLong(7, bean.getCourseId());
            pstmt.setString(8, bean.getCourseName());
            pstmt.setLong(9, bean.getSubjectId());
            pstmt.setString(10, bean.getSubjectName());
            pstmt.setString(11,bean.getSemester());
            pstmt.setString(12, bean.getEmailId());
            pstmt.setString(13, bean.getFatherEmailId());
            pstmt.setString(14, bean.getFatherMobileNo());
            pstmt.setString(15, bean.getProfilePic());
            pstmt.setString(16, bean.getCreatedBy());
            pstmt.setString(17, bean.getModifiedBy());
            pstmt.setTimestamp(18, bean.getCreatedDatetime());
            pstmt.setTimestamp(19, bean.getModifiedDatetime());
            pstmt.setLong(20,bean.getUserId());
            pstmt.setString(21,bean.getStatus());
            pstmt.setLong(22, bean.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Student ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    /**
     * Search Student
     * 
     * @param bean
     *            : Search Parameters
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public List search(StudentBean bean) throws ApplicationException {
        return search(bean, 0, 0);
    }

    /**
     * Search Student with pagination
     * 
     * @return list : List of Students
     * @param bean
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public List search(StudentBean bean, int pageNo, int pageSize)
            throws ApplicationException {
        log.debug("Model search Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM a_student WHERE 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getCourseId() > 0) {
                sql.append(" AND courseId = " + bean.getCourseId());
            }
            if (bean.getUserId() > 0) {
                sql.append(" AND userId = " + bean.getUserId());
            }
            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
                sql.append(" AND FIRSTNAME like '" + bean.getFirstName()
                        + "%'");
            }
            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
                sql.append(" AND LASTNAME like '" + bean.getLastName() + "%'");
            }
            if (bean.getDob() != null && bean.getDob().getDate() > 0) {
                sql.append(" AND DOB = " + bean.getDob());
            }
            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
                sql.append(" AND MOBILENO like '" + bean.getMobileNo() + "%'");
            }
            if (bean.getSemester() != null && bean.getSemester().length() > 0) {
                sql.append(" AND Semester like '" + bean.getSemester() + "%'");
            }
            if (bean.getEmailId() != null && bean.getEmailId().length() > 0) {
                sql.append(" AND EmailId like '" + bean.getEmailId() + "%'");
            }
           

        }

        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" Limit " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList list = new ArrayList();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getLong(2));
                bean.setFirstName(rs.getString(3));
                bean.setLastName(rs.getString(4));
                bean.setDob(rs.getDate(5));
                bean.setGender(rs.getString(6));
                bean.setMobileNo(rs.getString(7));
                bean.setCourseId(rs.getLong(8));
                bean.setCourseName(rs.getString(9));
                bean.setSubjectId(rs.getLong(10));
                bean.setSubjectName(rs.getString(11));
                bean.setSemester(rs.getString(12));
                bean.setEmailId(rs.getString(13));
                bean.setFatherEmailId(rs.getString(14));
                bean.setFatherMobileNo(rs.getString(15));
                bean.setProfilePic(rs.getString(16));
                bean.setCreatedBy(rs.getString(17));
                bean.setModifiedBy(rs.getString(18));
                bean.setCreatedDatetime(rs.getTimestamp(19));
                bean.setModifiedDatetime(rs.getTimestamp(20));
                bean.setUserId(rs.getLong(21));
                bean.setStatus(rs.getString(22));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model search End");
        return list;
    }

    /**
     * Get List of Student
     * 
     * @return list : List of Student
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * Get List of Student with pagination
     * 
     * @return list : List of Student
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     *  @throws ApplicationException
     */

    public List list(int pageNo, int pageSize) throws ApplicationException {
      System.out.println("in list method ");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from a_student");
        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StudentBean bean = new StudentBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getLong(2));
                bean.setFirstName(rs.getString(3));
                bean.setLastName(rs.getString(4));
                bean.setDob(rs.getDate(5));
                bean.setGender(rs.getString(6));
                bean.setMobileNo(rs.getString(7));
                bean.setCourseId(rs.getLong(8));
                bean.setCourseName(rs.getString(9));
                bean.setSubjectId(rs.getLong(10));
                bean.setSubjectName(rs.getString(11));
                bean.setSemester(rs.getString(12));
                bean.setEmailId(rs.getString(13));
                bean.setFatherEmailId(rs.getString(14));
                bean.setFatherMobileNo(rs.getString(15));
                bean.setProfilePic(rs.getString(16));
                bean.setCreatedBy(rs.getString(17));
                bean.setModifiedBy(rs.getString(18));
                bean.setCreatedDatetime(rs.getTimestamp(19));
                bean.setModifiedDatetime(rs.getTimestamp(20));
                bean.setUserId(rs.getLong(21));
                bean.setStatus(rs.getString(22));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model list End");
        return list;

    }

}
