package music.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import music.data.*;
import music.business.*;

public class ProdMaintServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // get the products and place into session var
    ServletContext sc = getServletContext();
    // String path = sc.getRealPath("/WEB-INF/products.txt");
    // ProductIO.init(path);
    
    HttpSession session = request.getSession();
    session.setAttribute("products", ProductDB.selectAll());
    session.setAttribute("error", null);
    
    // decide what to do based on action param
    String action = request.getParameter("action");
    if (action == null)
      action = "displayProducts";
    
    String url = "/prodList.jsp";
    switch (action)
    {
      case "displayProducts":
        url = "/prodList.jsp";
        break;
        
      case "addProduct":
        // set a product into the session if indicated, otherwise clear it
        if (request.getParameter("productCode") != null) {
          session.setAttribute("product", ProductDB.selectByCode(request.getParameter("productCode")));
        } else {
          session.setAttribute("product", null);
        }
        
        url = "/prodAdd.jsp";
        break;
        
      case "addConfirmed":
        // create temp product
        Product newProduct = ProductDB.selectByCode(request.getParameter("prodCode"));
        Boolean isInsert = false;
        if (newProduct == null) {
          isInsert = !isInsert;
          newProduct = new Product();
        }
        
        // populate these first, in case of Double error
        String prodCode = request.getParameter("prodCode");
        String prodDesc = request.getParameter("prodDescription");
        newProduct.setCode(prodCode);
        newProduct.setDescription(prodDesc);
        session.setAttribute("product", newProduct);
        
        try {
          // validate server side
          if (prodCode.isEmpty() || prodDesc.isEmpty()) {
            throw new Exception("Either product code or description is empty.");
          }
          
          // possible number error exception
          Double prodPrice = Double.parseDouble(request.getParameter("prodPrice"));
          newProduct.setPrice(prodPrice);
          session.setAttribute("product", newProduct);
          
          // decide between update and insert
          if (isInsert) {
            System.out.println("INSERT");
            ProductDB.insert(newProduct);
          } else {
            System.out.println("UPDATE");
            ProductDB.update(newProduct);
          }

          url = "/prodList.jsp";
        }
        catch (Exception e)
        {
          // user input is improper
          System.out.println(e);
          session.setAttribute("error", true);
          url = "/prodAdd.jsp";
        }
        break;
                
      case "deleteProduct":
        // set a product into the session if indicated, otherwise clear it
        if (request.getParameter("productCode") != null) {
          session.setAttribute("product", ProductDB.selectByCode(request.getParameter("productCode")));
        } else {
          session.setAttribute("product", null);
        }
        
        url = "/prodDelete.jsp";
        break;
        
      case "deleteConfirmed":
        Product delProduct = (Product) session.getAttribute("product");
        if (delProduct != null) {
          ProductDB.delete(delProduct);
        }
        
        url = "/prodList.jsp";
        break;
    }
    
    // do eet.
    sc.getRequestDispatcher(url).forward(request, response);
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }   
}
