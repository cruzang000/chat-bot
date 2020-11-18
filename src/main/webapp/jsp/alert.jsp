<%--  if error message exists show it  --%>
<c:if test="${error.length() > 0}">
    <section class="alert alert-danger" id="errorMessage">
       <ul>
           ${error}
       </ul>
    </section>
</c:if>

<c:if test="${success.length() > 0}">
    <section class="alert alert-success" id="errorMessage">
        ${success}
    </section>
</c:if>
