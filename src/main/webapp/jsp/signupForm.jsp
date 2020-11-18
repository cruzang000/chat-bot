<div>
    <form ACTION="${pageContext.request.contextPath}/userSignUp" METHOD="POST"
          id="signup-form" name="signup-form">
        <div class="d-flex flex-wrap justify-content-center">
            <fieldset class="col-6">
                <legend>Personal Info</legend>
                <div>
                    <label for="firstName">First Name:</label>
                    <input type="text" name="firstName" id="firstName"  
                           value="${error.length > 0 ? formValues.get("firstName") : ''}">
                </div>
                <div>
                    <label for="lastName">Last Name:</label>
                    <input type="text" name="lastName" id="lastName" 
                           value="${error.length > 0 ? formValues.get("lastName") : ''}">
                </div>
                <div>
                    <label for="dateOfBirth">Date of Birth:</label>
                    <input type="date" name="dateOfBirth" id="dateOfBirth" 
                           value="${error.length > 0 ? formValues.get("dateOfBirth") : ''}">
                </div>
                <div>
                    <label for="email">Email:</label>
                    <input type="text" name="email" id="email" 
                           value="${error.length > 0 ? formValues.get("email") : ''}">
                </div>
            </fieldset>
            <fieldset class="col-6">
                <legend>Login Info</legend>
                <div>
                    <label for="username">Username:</label>
                    <input type="text" name="username" id="username" 
                           value="${formValues ?  formValues.get("username"): ''}">
                </div>
                <div>
                    <label for="password">Create Password:</label>
                    <input type="password" name="password" id="password">
                    <label for="retypePassword">Retype Password:</label>
                    <input type="password" name="retypePassword" id="retypePassword" >
                </div>
            </fieldset>
        </div>

        <button type="submit" class="btn btn-success">Sign Up</button>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/signUpForm.js" type="module"></script>