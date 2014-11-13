/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.data;

import java.sql.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author Roy
 */
public class DBUtil {
    
  /*
  *
  * JPA Part
  *
  */
  //JPA Entity Manager factory
  private static final EntityManagerFactory emf =
    Persistence.createEntityManagerFactory("productPU");

  public static EntityManagerFactory getEmFactory() {
      return emf;
  }

  /*
  *
  * JDBC Part
  *
  *
  public static void closeStatment(Statement s) {
    try {
      if (s != null) {
        s.close();
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void closePreparedStatement(Statement ps) {
    try {
      if (ps != null) {
        ps.close();
      }
    } catch (SQLException e) {
      System.out.println(e);
    } 
  }

  public static void closeResultSet(ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
  */
}
