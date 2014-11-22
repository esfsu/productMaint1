<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pm" uri="/WEB-INF/music.tld" %>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />
    
<section>

<h1>Product</h1>

<p><pm:ifEmptyMark field=""/> marks required fields</p>

<c:if test="${error != null}">
  <p class="error">There was an error in your entry, please ensure all fields are filled out properly.</p>
</c:if>

<form action="addConfirmed" method="post">
  <label for="prodCode">Code:</label> 
    <input type="text" name="prodCode" value="<c:out value="${product.code}" />" required />
    <pm:ifEmptyMark field="${product.code}"/><br />
  <label for="prodDescription">Description:</label> 
    <input type="text" name="prodDescription" size="50" value="<c:out value="${product.description}" />" required />
    <pm:ifEmptyMark field="${product.description}"/><br />
  <label for="prodPrice">Price:</label> 
    <input type="text" name="prodPrice" value="<c:out value="${product.price}" />" required />
    <pm:ifEmptyMark field="${product.price}"/><br /><br />

  <input type="submit" value="Add Product">
</form><br/>

<form action="prodList" method="post">
  <input type="submit" value="View Products">
</form>
    
</section>

<jsp:include page="/includes/footer.jsp" />