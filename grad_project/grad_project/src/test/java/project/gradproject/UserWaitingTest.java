package project.gradproject;

import org.junit.jupiter.api.Test;
import project.gradproject.domain.store.Address;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UserWaitingTest {
    HttpServletRequest request;
    @Test
    void a(){
        List<User> users = new ArrayList<User>();
        System.out.println(users.size());
        users.add(new User());

        String s="서울특별시 서대문구 연희동 446-212  304호  ";
        String s1="서울";
        Address address = splitAddress(s);
        System.out.println(address.getState()+ ' ' + address.getCity()+ ' '+address.getTown()+' ' +address.getStreet()+' ' + address.getDetailAddress());
        System.out.println(address.getDetailAddress());
        System.out.println(s1.substring(0,2));
        System.out.println(users.size());
    }


    private Address splitAddress(String ad) {
        String s="";
        List<String> list = new ArrayList<>();
        boolean check=false;
        int index=0;
        for(int i=0;i<ad.length();i++){
            if(ad.charAt(i)!=' '){
                check=true;
                index=i;
                break;
            }
        }

        if(check){
            for(int i = index; i< ad.length(); i++){
                if(ad.charAt(i)==' ') {
                    if(s=="") continue;
                    list.add(s);
                }
                else {
                    s+= ad.charAt(i);
                    continue;
                }
                s="";
            }
        }
        list.add(s);
        String str="";
        for(int i=4;i<list.size();i++){
            str+=list.get(i)+' ';
        }
        Address address = new Address(list.get(0),list.get(1),list.get(2),list.get(3),str);
        return address;
    }
}
