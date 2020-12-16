<html>
<%-- include head --%>
<%@ include file = "../head.jsp" %>

<body class="bg-blue-light">

<%-- include nav bar --%>
<%@ include file = "../../jsp/header.jsp" %>

<main>
    <div class="d-flex flex-wrap">
        <section class="sidebar col-4">
            <ul class="list-unstyled">
                <li class="p-2">
                    ${sessionScope.getOrDefault("user", null).fullName} <a href="#" class="btn btn-sm btn-link">Edit Profile</a>
                </li>
                <li class="p-2">
                    <a href="#" class="btn btn-sm p-0">Friends</a>
                </li>
                <li class="p-2">
                    <a href="#" class="btn btn-sm p-0">Notifications</a>
                </li>
            </ul>
            <section class="bg-white">
                <h3 class="bg-dark-navy text-center text-white"><u>Current Plans</u></h3>
                <ul class="list-unstyled">
                    <c:forEach items="${currentUserPlans}" var="plan">
                        <li class="border d-flex flex-row">
                            <figure class="col-4 m-0 d-flex flex-column align-items-center">
                                <img src="${plan.location.imageUrl}" alt="card-image" class="locationSearchImage">
                                <ul class="star-rating d-flex flex-row list-unstyled mt-auto align-self-end"></ul>
                            </figure>
                            <ul>
                                <li>${plan.location.name}</li>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </section>
        </section>
    </div>
</main>

</body>

</html>
