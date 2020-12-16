<%-- include head --%>
<%@ include file = "/jsp/head.jsp" %>

<body>

    <%-- include nav bar --%>
    <%@ include file = "/jsp/header.jsp" %>

    <section class="col-10 col-sm-7 col-md-6 col-lg-5 col-xl-4 my-5 mx-auto text-white p-5 shadow-lg bg-white">
        <form ACTION="j_security_check" METHOD="POST" class="d-flex flex-wrap justify-content-center my-0">
            <div class="form-group col-12 text-center p-0">
                <input type="text" name="j_username" id="j_username" class="form-control bg-grey-brown py-4 border-0"
                   placeholder="username" required>
            </div>
            <div class="form-group col-12 text-center p-0">
                <input type="password" name="j_password" id="j_password" class="form-control bg-grey-brown py-4 border-0"
                   placeholder="password"  required>
            </div>
            <div class="col-12 text-center p-0 pt-3">
                <button type="submit" id="j_submit" class="btn bg-blue-purple text-white rounded-0 col-12 py-3">LOGIN</button>
            </div>
        </form>
    </section>
</body>


