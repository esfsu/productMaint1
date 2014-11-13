/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.data;

import java.util.List;
import music.business.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Roy
 */
public class ProductDB {
   
//   JDBC Part
//   public static ArrayList<Product> selectAll() {
//    ConnectionPool pool = ConnectionPool.getInstance();
//    Connection connection = pool.getConnection();
//    PreparedStatement ps = null;
//    ResultSet rs = null;
//       
//    String preparedQuery = "SELECT * FROM Product";    
//        try {
//            ps = connection.prepareStatement(preparedQuery);
//            rs = ps.executeQuery();
//            ArrayList<Product> products = new ArrayList<Product>();
//            while (rs.next())
//            {
//                Product product = new Product();
//                product.setCode(rs.getString("ProductCode"));
//                product.setDescription(rs.getString("ProductDescription"));
//                product.setPrice(rs.getDouble("ProductPrice"));
//                products.add(product);
//            }
//            return products;
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        } finally {
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);    
//    }
//  }
   
//    public static Product selectByCode(String code) {
//    ConnectionPool pool = ConnectionPool.getInstance();
//    Connection connection = pool.getConnection();
//    PreparedStatement ps = null;
//    ResultSet rs = null;
//
//        String preparedQuery = "SELECT * FROM Product "
//        + "WHERE ProductCode = ?";
//    try {
//        ps = connection.prepareStatement(preparedQuery);
//        ps.setString(1, code);
//        rs = ps.executeQuery();
//        rs.next();
//        
//        Product product = new Product();
//        product.setCode(rs.getString("ProductCode"));
//        product.setDescription(rs.getString("ProductDescription"));
//        product.setPrice(rs.getDouble("ProductPrice"));
//        return product;
//            } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        } finally {
//          DBUtil.closeResultSet(rs);
//          DBUtil.closePreparedStatement(ps);
//          pool.freeConnection(connection);
//    }
//  }  

//   public static Boolean codeExists(String code) {
//  
//   ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String preparedQuery = "SELECT * FROM Product "
//                + "WHERE ProductCode = ?";
//        try {
//            ps = connection.prepareStatement(preparedQuery);
//            ps.setString(1, code);
//            rs = ps.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            System.out.println(e);
//            return false;
//        } finally {
//            DBUtil.closeResultSet(rs);
//            DBUtil.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//        }
//    }
    
//   public static int update(Product product)  {
//    ConnectionPool pool = ConnectionPool.getInstance();
//    Connection connection = pool.getConnection();
//    PreparedStatement ps = null;
//       
//    String preparedSQL = "UPDATE Product SET "
//                    + " ProductCode = ?, "
//                    + " ProductDescription = ?, "
//                    + " ProductPrice = ?"
//                    + "WHERE ProductCode = ?";
//    try{
//      ps = connection.prepareStatement(preparedSQL);
//      ps.setString(1, product.getCode());
//      ps.setString(2, product.getDescription());
//      ps.setDouble(3, product.getPrice());
//      ps.setString(4, product.getCode());
//            
//        return ps.executeUpdate();
//    } catch (SQLException e) {
//        System.out.println(e);
//        return 0;
//    } finally {
//        DBUtil.closePreparedStatement(ps);
//        pool.freeConnection(connection);
//    }
//}

 //  public static int insert(Product product) {   
//       ConnectionPool pool = ConnectionPool.getInstance();
//       Connection connection = pool.getConnection();
//       PreparedStatement ps = null;
//       
//       String preparedQuery =
//               "INSERT INTO Product (ProductCode, ProductDescription, ProductPrice) "
//               + "VALUES (?, ?, ?)";
//    try {
//       ps = connection.prepareStatement(preparedQuery);
//       ps.setString(1, product.getCode());
//       ps.setString(2, product.getDescription());
//       ps.setDouble(3, product.getPrice());
//       return ps.executeUpdate();
//    } catch (SQLException e) {
//        System.out.println(e);
//        return 0;
//    } finally {
//        DBUtil.closePreparedStatement(ps);
//        pool.freeConnection(connection);
//    }
// }

//   public static int delete(Product product)  {
//       ConnectionPool pool = ConnectionPool.getInstance();
//       Connection connection = pool.getConnection();
//       PreparedStatement ps = null;
//       
//    String preparedQuery = "DELETE FROM Product "
//            + "WHERE ProductCode = ?";
//    try {
//    ps = connection.prepareStatement(preparedQuery);
//    ps.setString(1, product.getCode());
//    
//    return ps.executeUpdate();
//    } catch (SQLException e) {
//        System.out.println(e);
//        return 0;
//    } finally {
//        DBUtil.closePreparedStatement(ps);
//        pool.freeConnection(connection);
//    } 
//  }
// JDBC Part End
    
       
  // JPA Part
  public static List<Product> selectAll() {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    String qString = "SELECT u FROM Product u";
    TypedQuery<Product> q = em.createQuery(qString, Product.class);

    List<Product> products;
    try {
      products = q.getResultList();
    } catch (NoResultException e) {
      System.out.println(e);
      products = null;
    } finally {
      em.close();
    }
    return products;
  }
       
  public static Product selectByCode(String code) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    String qString = "SELECT u FROM Product u WHERE u.code = :code";
    TypedQuery<Product> q = em.createQuery(qString, Product.class);
    q.setParameter("code", code);
    
    Product product = null;
    try {
      product = q.getSingleResult();
    } catch (NoResultException e) {
      System.out.println(e);
      product = null;
    } finally {
      em.close();
    }
    return product;
  }
      
  public static void update(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
      trans.begin();
      em.merge(product);
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }
  }
    
  public static void insert(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
      trans.begin();
      em.persist(product);
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }
  }
   
  public static void delete(Product product) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    EntityTransaction trans = em.getTransaction();      
    try {
      trans.begin();
      em.remove(em.merge(product));
      trans.commit();
    } catch (Exception e) {
      System.out.println(e);
      trans.rollback();
    } finally {
      em.close();
    }       
  }
  // JPA Part End
  
  // closing public class ProductDB 
}
