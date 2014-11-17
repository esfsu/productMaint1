<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../includes/header.html" %>
    
<h1>Products</h1>

<table class="border">
  <tr>
    <th>Code</th>
    <th>Description</th>
    <th class="right">Price</th>
    <th>&nbsp;</th>
    <th>&nbsp;</th>
  </tr>
  <c:choose>
    <c:when test="${products != '[]'}">
      <c:forEach var="product" items="${products}">
      <tr>
        <td><c:out value="${product.code}" /></td>
        <td><c:out value="${product.description}" /></td>
        <td class="right"><c:out value="${product.price}" /></td>
        <td><a href="productMaint?action=addProduct&productCode=${product.code}">Edit</a></td>
        <td><a href="productMaint?action=deleteProduct&productCode=${product.code}">Delete</a></td>
      </tr>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <tr>
        <td colspan="5">There are no products found.</td>
      </tr>
    </c:otherwise>
  </c:choose>
</table><br />
    
<form action="productMaint" method="post">
  <input type="hidden" name="action" value="addProduct" />
  <input type="submit" value="Add Product" />
</form>
        
</body>
</html>