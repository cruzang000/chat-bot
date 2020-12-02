<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>

    <section class="d-flex flex-wrap">
        <nav class="navbar navbar-expand-sm bg-dark-navy text-white col-12 p-0">
            <h1 class="text-right">SociaLite</h1>
            <!--        <form class="form-inline" action="/action_page.php">-->
            <!--            <input class="form-control mr-sm-2" type="text" placeholder="Search">-->
            <!--            <button class="btn btn-success" type="submit">Search</button>-->
            <!--        </form>-->
            <ul class="list-unstyled d-flex flex-wrap">
                <li>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-outline-light">Find Plans</a>
                </li>
                <li>
                    <button type="button" class="btn btn-outline-light modalTrigger" value="signup" data-toggle="modal"
                            data-target="#genericModal" data-template="${pageContext.request.contextPath}/jsp/signupForm.jsp">
                        Sign Up
                    </button>
                </li>
                <li>
                    <a href="/socialite/home" class="btn btn-outline-light">
                        ${sessionScope.getOrDefault("user", null) == null ? "login" : "home"}
                    </a>
                </li>
            </ul>
        </nav>
    </section>

    <script>
        <%--appData.user = "${sessionScope.getOrDefault("user", null)}";--%>
        <%--appStorageObject.updateSessionStorage(appData);--%>

        <%--console.log(appData.user)--%>
    </script>

</header>


<%-- modal template for js --%>
<%@ include file = "/jsp/genericModal.jsp" %>
