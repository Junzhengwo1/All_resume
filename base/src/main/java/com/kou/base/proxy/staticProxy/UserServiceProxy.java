package proxy.staticProxy;

/**
 * @author JIAJUN KOU
 */
public class UserServiceProxy implements UserService {

    private UserServiceImpl userService;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void add() {
        log("add");
        userService.add();
    }

    @Override
    public void delete() {
        log("delete");
        userService.delete();
    }

    @Override
    public void update(){
        log("update");
        userService.delete();
    }

    @Override
    public void query() {
        log("query");
        userService.query();
    }

    //日志方法

    public void log(String msg){
        System.out.println("使用了"+msg+"方法");
    }

}
