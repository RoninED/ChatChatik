package server;

/**
 * Created by Eduard on 2/12/2018.
 */
public class TemporarySQLdata {
    String login;
    String password;

    public TemporarySQLdata(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public  static  TemporarySQLdata[] createDataUsers () {

        TemporarySQLdata [] tipaSQLdata  = new TemporarySQLdata[3];

        tipaSQLdata [0] = new TemporarySQLdata("admin", "admin");
        tipaSQLdata [1] = new TemporarySQLdata("1", "1");
        tipaSQLdata [2] = new TemporarySQLdata("q", "q");
        return tipaSQLdata;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


}
