<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="d-flex flex-wrap">
    <nav class="navbar navbar-expand-sm bg-dark-navy text-white col-12 p-0">
        <h1 class="text-right">SociaLite</h1>
<!--        <form class="form-inline" action="/action_page.php">-->
<!--            <input class="form-control mr-sm-2" type="text" placeholder="Search">-->
<!--            <button class="btn btn-success" type="submit">Search</button>-->
<!--        </form>-->
        <ul class="list-unstyled">
            <ul class="list-unstyled d-flex flex-wrap">
                <li>
                    <button class="btn btn-outline-light">Sign Up</button>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-outline-light">Find Plans</a>
                </li>
                <li>
                    <a href="#" title="Login" data-toggle="loginPopover" class="btn btn-outline-light popoverTrigger"
                       data-template-file="${pageContext.request.contextPath}/jsp/member/loginForm.jsp">Login</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/jsp/member/memberHome.jsp" class="btn btn-outline-light">
                        Member Home(for testing)</a>
                </li>
            </ul>
        </ul>
    </nav>
</section>