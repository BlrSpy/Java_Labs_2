package LabaTags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import LabaPacks.Ad;
import LabaPacks.AdList;
import LabaPacks.User;

public class GetAds extends SimpleTagSupport {

    private int id = 0;

    // ���� ������ ��� �������� range (�������� ����������)
    private String range;

    // ���� ������ ��� �������� sort (��������� ����������)
    private String sort;

    // ���� ������ ��� �������� dir (����������� ����������)
    private char dir;

    // ���� ������ ��� �������� var (��������� ����������)
    private String var;

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setId(int id) {
        this.id = id;
    }

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setRange(String range) {
        this.range = range.toLowerCase();
    }

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setSort(String sort) {
        this.sort = sort.toLowerCase();
    }

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setDir(char dir) {
        this.dir = Character.toLowerCase(dir);
    }

    // �����-������ ��� ��������� �������� (���������� �����������)
    public void setVar(String var) {
        this.var = var;
    }


    public void doTag() throws JspException, IOException {

        // ������� �� ��������� ���������� ����� ������ ����������
        final AdList adList = (AdList) getJspContext().getAttribute("ads",PageContext.APPLICATION_SCOPE);
        if (id>0) {

            // ���� ��������� ������� ������ ������ 1 ����������
            for (Ad ad: adList.getAds()) {
                if (ad.getId()==id) {

                    // ��������� ��������� ���������� � ���������� varName
                    getJspContext().setAttribute(GetAds.this.var, ad, PageContext.PAGE_SCOPE);
                }
            }
        } else {

            // ���������� ���������� ������� ����������
            // ������� �� ������ bean �������������������� ������������
            final User authUser = (User)getJspContext().getAttribute("authUser", PageContext.SESSION_SCOPE);

            // � ���� ������ ����� ����������� ������ ����������  ����������
            ArrayList<Ad> sortedList = new ArrayList<Ad>();

            // ���������������� ��� ���������� �� ����� ����������
            for (Ad ad: adList.getAds()) {

                // ���� ����� ���������� ����������� ��������� ��������
                // ��� ������� ���������� ����������� ������������
                if (!"my".equals(range) || (authUser!=null &&  authUser.getId()==ad.getAuthorId())) {

                    // �������� ���������� � ������
                    sortedList.add(ad);
                }
            }

            // ��� ��������� ����� ���������� � ������� ���������  �����������
            // ������ ���������� ����� �������� �� ���������� ����������  �������� ��������
            Comparator<Ad> comparator = new Comparator<Ad>() {
                public int compare(Ad ad1, Ad ad2) {
                    int result;

                    // ���� ������� ���������� �� ���� ����������  ��������� ����������
                    if (GetAds.this.sort!=null &&
                            GetAds.this.sort.equals("date")) {

                        // �� ��������� ������������ �� ���������  ���� lastModified
                        result =  ad1.getLastMoidfied()<ad2.getLastMoidfied()?- 1:(ad1.getLastMoidfied()>ad2.getLastMoidfied()?1:0);

                        // ���� ������� ���������� �������� - 	 ����������� ��������� ���������
                        if (GetAds.this.dir=='d') {
                            result = -result;
                        }
                    } else

                        // ���� ������� ���������� �� ���� ����������
                        if (GetAds.this.sort!=null &&  GetAds.this.sort.equals("subject")) {

                            // �� ��������� ������������ �� ���������    ���� subject
                            result =  ad1.getSubject().compareTo(ad2.getSubject());

                            // ���� ������� ���������� �������� - ����������� ��������� ���������
                            if (GetAds.this.dir=='d') {
                                result = -result;
                            }
                        } else {

                            // ����� ��������� �� ������ ���������� �� ��������� ���� name ������
                            result =  ad1.getAuthor().getName().compareTo(ad2.getAuthor().getName());

                            // ���� ������� ���������� �������� -   ����������� ��������� ���������
                            if (GetAds.this.dir=='d') {
                                result = -result;
                            }
                        }
                    return result;
                }
            };
            if (sortedList.size()==0) {

                // ���� �� ������� �� ����� ������, �� ���������� null
                sortedList = null;
            } else {

                // ������������� ������
                Collections.sort(sortedList, comparator);
            }

            // ��������� ��������������� ������ � ���������� � ������      varName
            // � ��������� ��������
            getJspContext().setAttribute(GetAds.this.var, sortedList, PageContext.PAGE_SCOPE);
        }
    }
}