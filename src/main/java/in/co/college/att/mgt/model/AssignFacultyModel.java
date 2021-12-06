package in.co.college.att.mgt.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.college.att.mgt.bean.AssignFacultyBean;
import in.co.college.att.mgt.bean.SubjectBean;
import in.co.college.att.mgt.exception.ApplicationException;
import in.co.college.att.mgt.exception.DatabaseException;
import in.co.college.att.mgt.exception.DuplicateRecordException;
import in.co.college.att.mgt.util.JDBCDataSource;

public class AssignFacultyModel {
	
private static Logger log = Logger.getLogger(AssignFacultyModel.class);

    
    public Integer nextPK() throws DatabaseException {
        log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT MAX(ID) FROM a_assignfaculty");
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

   
    public long add(AssignFacultyBean bean) throws ApplicationException,
            DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        int pk = 0;
		AssignFacultyBean duplicataAssignFaculty = findBySubNameAndFac(bean.getUserId(),bean.getSubjectId());

        // Check if create AssignFaculty already exist
        if (duplicataAssignFaculty != null) {
            throw new DuplicateRecordException("Faculty Are Already assign in this Subject");
        }
        
        SubjectModel sModel=new SubjectModel();
        SubjectBean sBean=sModel.findByPK(bean.getSubjectId());
        
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();

            // Get auto-generated next primary key
            System.out.println(pk + " in ModelJDBC");
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO a_assignfaculty VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setLong(2,bean.getUserId());
            pstmt.setString(3,bean.getUserName());
            pstmt.setLong(4,sBean.getCourseId());
            pstmt.setString(5,sBean.getCourseName());
            pstmt.setLong(6,sBean.getId());
            pstmt.setString(7,sBean.getName());
            pstmt.setString(8,sBean.getSemester());
            pstmt.setString(9, bean.getTotalClass());
            pstmt.setString(10, bean.getCreatedBy());
            pstmt.setString(11, bean.getModifiedBy());
            pstmt.setTimestamp(12, bean.getCreatedDatetime());
            pstmt.setTimestamp(13, bean.getModifiedDatetime());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add AssignFaculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

   
	    public void delete(AssignFacultyBean bean) throws ApplicationException {
	        log.debug("Model delete Started");
	        Connection conn = null;
	        try {
	            conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false); // Begin transaction
	            PreparedStatement pstmt = conn
	                    .prepareStatement("DELETE FROM a_assignfaculty WHERE ID=?");
	            pstmt.setLong(1, bean.getId());
	            pstmt.executeUpdate();
	            conn.commit(); // End transaction
	            pstmt.close();
	        } catch (Exception e) {
	          //  log.error("Database Exception..", e);
	            try {
	                conn.rollback();
	            } catch (Exception ex) {
	                throw new ApplicationException(
	                        "Exception : Delete rollback exception "
	                                + ex.getMessage());
	            }
	            throw new ApplicationException(
	                    "Exception : Exception in delete AssignFaculty");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	        log.debug("Model delete Started");
	    }

    public AssignFacultyBean findBySubNameAndFac(long userid,long subId) throws ApplicationException {
        log.debug("Model findBy EmailId Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM a_assignfaculty WHERE userId=? and subjectId=?");
        AssignFacultyBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, userid);
            pstmt.setLong(2,subId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new AssignFacultyBean();
                bean.setId(rs.getLong(1));
                bean.setUserId(rs.getLong(2));
                bean.setUserName(rs.getString(3));
                bean.setCourseId(rs.getLong(4));
                bean.setCourseName(rs.getString(5));
                bean.setSubjectId(rs.getLong(6));
                bean.setSubjectName(rs.getString(7));
                bean.setSemester(rs.getString(8));
                bean.setTotalClass(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException(
                    "Exception : Exception in getting User by emailId");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findBy EmailId End");
        return bean;
    }
    
    
    public AssignFacultyBean findBySubName(long subId) throws ApplicationException {
        log.debug("Model findBy EmailId Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM a_assignfaculty WHERE  subjectId=?");
        AssignFacultyBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1,subId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new AssignFacultyBean();
                bean.setId(rs.getLong(1));
                bean.setUserId(rs.getLong(2));
                bean.setUserName(rs.getString(3));
                bean.setCourseId(rs.getLong(4));
                bean.setCourseName(rs.getString(5));
                bean.setSubjectId(rs.getLong(6));
                bean.setSubjectName(rs.getString(7));
                bean.setSemester(rs.getString(8));
                bean.setTotalClass(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException(
                    "Exception : Exception in getting User by emailId");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findBy EmailId End");
        return bean;
    }

   
    public AssignFacultyBean findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM a_assignfaculty WHERE ID=?");
        AssignFacultyBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new AssignFacultyBean();
                bean.setId(rs.getLong(1));
                bean.setUserId(rs.getLong(2));
                bean.setUserName(rs.getString(3));
                bean.setCourseId(rs.getLong(4));
                bean.setCourseName(rs.getString(5));
                bean.setSubjectId(rs.getLong(6));
                bean.setSubjectName(rs.getString(7));
                bean.setSemester(rs.getString(8));
                bean.setTotalClass(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
            }
            rs.close();
        } catch (Exception e) {
           log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return bean;
    }

  
    public void update(AssignFacultyBean bean) throws ApplicationException,
            DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        AssignFacultyBean duplicataAssignFaculty = findBySubNameAndFac(bean.getUserId(),bean.getSubjectId());

        // Check if updated AssignFaculty already exist
        if (duplicataAssignFaculty != null && duplicataAssignFaculty.getId() != bean.getId()) {
            throw new DuplicateRecordException("AssignFaculty already exists");
        }
        
        SubjectModel sModel=new SubjectModel();
        SubjectBean sBean=sModel.findByPK(bean.getSubjectId());
       
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE a_assignfaculty SET UserId=?,UserName=?,CourseId=?,subjectId=?,"
                    		+ "subjectNAME=?,semester=?,TotalClass=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
            pstmt.setLong(2,bean.getUserId());
            pstmt.setString(3,bean.getUserName());
            pstmt.setLong(4,sBean.getCourseId());
            pstmt.setString(5,sBean.getCourseName());
            pstmt.setLong(6,sBean.getId());
            pstmt.setString(7,sBean.getName());
            pstmt.setString(8,sBean.getSemester());
            pstmt.setString(9, bean.getTotalClass());
            pstmt.setString(10, bean.getCreatedBy());
            pstmt.setString(11, bean.getModifiedBy());
            pstmt.setTimestamp(12, bean.getCreatedDatetime());
            pstmt.setTimestamp(13, bean.getModifiedDatetime());
            pstmt.setLong(7, bean.getId());
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
            throw new ApplicationException("Exception in updating AssignFaculty ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

 
    public List search(AssignFacultyBean bean) throws ApplicationException {
        return search(bean, 0, 0);
    }

   
    public List search(AssignFacultyBean bean, int pageNo, int pageSize)
            throws ApplicationException {
        log.debug("Model search Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM a_assignfaculty WHERE 1=1");
        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            
            if (bean.getUserId() > 0) {
                sql.append(" AND userId = " + bean.getUserId());
            }
            if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" AND SubjectName LIKE '" + bean.getSubjectName() + "%'");
            }
            if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" AND UserName LIKE '" + bean.getUserName() + "%'");
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
                bean = new AssignFacultyBean();
                bean.setId(rs.getLong(1));
                bean.setUserId(rs.getLong(2));
                bean.setUserName(rs.getString(3));
                bean.setCourseId(rs.getLong(4));
                bean.setCourseName(rs.getString(5));
                bean.setSubjectId(rs.getLong(6));
                bean.setSubjectName(rs.getString(7));
                bean.setSemester(rs.getString(8));
                bean.setTotalClass(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
           log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search AssignFaculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model search End");
        return list;
    }

   
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from a_assignfaculty");
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
                AssignFacultyBean bean = new AssignFacultyBean();
                bean.setId(rs.getLong(1));
                bean.setUserId(rs.getLong(2));
                bean.setUserName(rs.getString(3));
                bean.setCourseId(rs.getLong(4));
                bean.setCourseName(rs.getString(5));
                bean.setSubjectId(rs.getLong(6));
                bean.setSubjectName(rs.getString(7));
                bean.setSemester(rs.getString(8));
                bean.setTotalClass(rs.getString(9));
                bean.setCreatedBy(rs.getString(10));
                bean.setModifiedBy(rs.getString(11));
                bean.setCreatedDatetime(rs.getTimestamp(12));
                bean.setModifiedDatetime(rs.getTimestamp(13));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
          //  log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of AssignFaculty");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model list End");
        return list;

    }

}
