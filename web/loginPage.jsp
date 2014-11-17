<%@ include file="../includes/header.html" %>

<!DOCTYPE html>
<html>

    <body>
        <h1>Login</h1>
        <p>Please enter your Username and Password to proceed</p>
        <form action="j_security_check" method="get">
            <label class="pad_top">Username</label>
            <input type="text" name="j_username"><br>
            <label class="pad_top">Password</label>
            <input type="password" name="j_password"><br>
            <label>&nbsp;</label>
            <input type="submit" value="Login" class="margin_left">
        </form>
    </body>
</html>