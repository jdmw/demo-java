/*

class AuthServiceRequest {
    private String serviceName;
    public AuthServiceRequest(String serviceName){
        this.serviceName = serviceName;
    }
    */
/**
     * 取得指定服务器名称的认证消息
     * @return request – 返回一条十六进制消息
     **//*

    public byte [] getRequestMessage() {
        byte [] request;
        ProcessTypes type = new ProcessTypes();
        type.asByte(AuthConstant.SSH_MSG_SERVICE_REQUEST);
        type.asString(serviceName);
        request = type.getBytes();
        return request;
    }}

class AuthRequestByNone {
    private String userName;
    private String serviceName;
    public AuthRequestByNone(String serviceName, String user) {
        this.serviceName = serviceName;
        this.userName = user;
    }

    */
/**
     * 取得指定服务器名称和用户名的认证消息
     * @return request - 返回一条十六进制消息
     * *//*

    public byte [] getRequestMessage() {
        byte [] request;
        ProcessTypes type = new ProcessTypes();
        type.asByte(AuthConstant.SSH_MSG_USERAUTH_REQUEST);
        type.asString(userName);
        type.asString(serviceName);
        type.asString(AuthConstant.SSH_NONE_AUTHENTICATION_METHOD);
        request = type.getBytes();
        return request;
    }
}

class Main {


    public boolean initialize(String userName) throws IOException {
        // 预处理服务名称的请求
        AuthServiceRequest serviceRequest = new
                AuthServiceRequest(AuthConstant.SSH_SERVICE_NAME);
        IManager transManager = ManagerFactory.getManager(Constant.TRANSPORT_LAYER);
        transManager.sendMessage(serviceRequest.getRequestMessage());
        // 处理无认证方式的消息请求
        AuthRequestByNone authNone = new
                AuthRequestByNone(AuthConstant.SSH_CONN_SERVICE_NAME, userName);
        transManager.sendMessage(authNone.getRequestMessage());
        byte[] message = getMessage();
        // 验证当前的服务名称是否合法
        if (!isAccepted(message)) {
            return false;
        }
        // 取得无认证方式的请求数据包
        message = getMessage();
        // 验证当前的请求是否成功
        if (isRequestFailed(message)) {
            return false;
        }
        return true;
    }

    private boolean isRequestFailed(byte[] messages) throws IOException {
        if (messages[0] == AuthConstant.SSH_MSG_USERAUTH_SUCCESS) {
            return true;
        }
        if (messages[0] == AuthConstant.SSH_MSG_USERAUTH_FAILURE) {
            AuthFailure failure = new AuthFailure(messages);
            authentications = failure.getAuthThatCanContinue();
            isPartialSuccess = failure.isPartialSuccess();
            return false;
        }
        throw new IOException("Unexpected SSH message (type " + messages[0] + ")");
    }

    public byte[] getRequestMessage() {
        byte[] request;
        ProcessTypes type = new ProcessTypes();
        type.asByte(AuthConstant.SSH_MSG_USERAUTH_REQUEST);
        type.asString(userName);
        type.asString(serviceName);
        type.asString(AuthConstant.SSH_PASSWORD_AUTHENTICATION_METHOD);
        type.asString(password);
        request = type.getBytes();
        return request;
    }

    public boolean passwordAuthentication(String user, String pass) throws IOException {
        // 初始化请求
        initialize(user);
        // 验证指定的认证方式是否是 SSH 服务器所支持的
        if (verifyAuthenticatonMethods(AuthConstant.SSH_PASSWORD_AUTHENTICATION_METHOD)) {
            return false;
        }
        // 调用密码认证方式
        AuthRequestByPassword passwordRequest = new
                AuthRequestByPassword(AuthConstant.SSH_CONN_SERVICE_NAME, user, pass);
        // 发送一个消息请求到服务器端
        IManager transManager = ManagerFactory.getManager(Constant.TRANSPORT_LAYER);
        transManager.sendMessage(passwordRequest.getRequestMessage());
        // 从服务器端获取数据包
        byte[] message = getMessage();
        // 验证当前的请求是否成功
        if (isRequestFailed(message)) {
            return false;
        }
        return true;
    }
}*/
