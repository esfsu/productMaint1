package music.admin;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import music.data.*;
import music.business.*;

public class ProductController extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    // get the products and place into session var
    //ServletContext sc = getServletContext();
    // String path = sc.getRealPath("/WEB-INF/products.txt");
    // ProductIO.init(path);
    
    HttpSession session = request.getSession();
    session.setAttribute("error", null);
    
    // decide what to do based on action param
    //String action = request.getParameter("action");
    //if (action == null)
      //action = "displayProducts";
    
    String requestURI = request.getRequestURI();
    String url = "";
    System.out.println("requestURI=" + requestURI);
    
    if (requestURI.endsWith("/addProduct"))
    {
      // set a product into the session if indicated, otherwise clear it
      if (request.getParameter("productCode") != null) {
        session.setAttribute("product", ProductDB.selectByCode(request.getParameter("productCode")));
      } else {
        session.setAttribute("product", null);
      }

      url = "/admin/prodAdd.jsp";
    }
    else if (requestURI.endsWith("/addConfirmed"))
    {
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
          ProductDB.insert(newProduct);
        } else {
          ProductDB.update(newProduct);
        }

        session.setAttribute("products", ProductDB.selectAll());
        url = "/admin/prodList.jsp";
      }
      catch (Exception e)
      {
        // user input is improper
        System.out.println(e);
        session.setAttribute("error", true);
        url = "/admin/prodAdd.jsp";
      }
    }
    else if (requestURI.endsWith("/deleteProduct"))
    {
      // set a product into the session if indicated, otherwise clear it
      if (request.getParameter("productCode") != null) {
        session.setAttribute("product", ProductDB.selectByCode(request.getParameter("productCode")));
      } else {
        session.setAttribute("product", null);
      }

      url = "/admin/prodDelete.jsp";
    }
    else if (requestURI.endsWith("/deleteConfirmed"))
    {  
      Product delProduct = (Product) session.getAttribute("product");
      if (delProduct != null) {
        ProductDB.delete(delProduct);
      }

      session.setAttribute("products", ProductDB.selectAll());
      url = "/admin/prodList.jsp";
    }
    else if (requestURI.endsWith("/prodList"))
    {
      // if none of those, show all products
      session.setAttribute("products", ProductDB.selectAll());
      url = "/admin/prodList.jsp";
    }
    
    // do eet.
    if (url.equals("") != true)
    {
      System.out.println("url=" + url);
      getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    else
    {
      // display 404 error page
      response.sendError(404);
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }  
}
