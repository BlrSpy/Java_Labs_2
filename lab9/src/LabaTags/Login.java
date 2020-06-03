package LabaTags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import LabaPacks.User;
import LabaPacks.UserList;

public class Login extends SimpleTagSupport {

    // ���� ������ ��� �������� login
    private String login;

    // ���� ������ ��� �������� password
    private String password;

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setLogin(String login) {
        this.login = login;
    }

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setPassword(String password) {
        this.password = password;
    }

    public void doTag() throws JspException, IOException {

        // ���������� �������� ������ = null (�.�. ������ ���)
        String errorMessage = null;

        // ������� �� ��������� ���������� ����� ������ �������������
        UserList userList = (UserList) 	getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);

        if (login==null || login.equals("")) {
            errorMessage = "Введите логин!";
        } else {

            // ����� ������������ � ����� �������
            User user = userList.findUser(login);

            // ���������������� ��������� ������
            if (user==null || !user.getPassword().equals(password)) {

                // ������������ � ����� ������� �� ������ ��� ������������ ������
                errorMessage = "Неверный пароль!";
            } else {

                // ����� � ������ �����, �����������������  ������������ �������� user � ������
                getJspContext().setAttribute("authUser", user,  PageContext.SESSION_SCOPE);
            }
        }
        // ��������� �������� ������ (����� ��� null) � ������
        getJspContext().setAttribute("errorMessage", errorMessage,  PageContext.SESSION_SCOPE);
    }
}
