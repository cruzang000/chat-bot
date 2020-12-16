<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>

    <section class="d-flex flex-wrap">
        <nav class="navbar navbar-expand-sm bg-dark-navy text-white col-12 p-0 flex-wrap justify-content-sm-end py-sm-2">
            <h1 class="col-12 col-sm-5 col-md-4 col-lg-3 col-xl-2 text-center text-sm-right my-1 px-0">SociaLite</h1>
            <!--        <form class="form-inline" action="/action_page.php">-->
            <!--            <input class="form-control mr-sm-2" type="text" placeholder="Search">-->
            <!--            <button class="btn btn-success" type="submit">Search</button>-->
            <!--        </form>-->
            <ul class="list-unstyled d-flex flex-wrap my-0 col-12 col-sm-6 col-md-5 col-lg-4 col-xl-3 justify-content-center
                    px-0 py-2 pb-3 p-sm-0">
                <li class="mx-1">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-outline-light">Find Plans</a>
                </li>

                <c:if test = "${sessionScope.getOrDefault('user', null) == null}">
                <li class="mx-1">
                    <button type="button" class="btn btn-outline-light modalTrigger" value="Signup" data-toggle="modal"
                            data-target="#genericModal" data-template="${pageContext.request.contextPath}/jsp/signupForm.jsp">
                        Sign Up
                    </button>
                </li>
                </c:if>
                <li class="mx-1">
                    <a href="/socialite/home" class="btn btn-outline-light">
                        ${sessionScope.getOrDefault("user", null) == null ? "login" : "home"}
                    </a>
                </li>
                <c:if test = "${sessionScope.getOrDefault('user', null) != null}">
                    <li class="mx-1">
                        <a href="/socialite/logout" class="btn btn-outline-light">Log Out</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </section>

</header>


<%-- modal template for js --%>
<%@ include file = "/jsp/genericModal.jsp" %>
