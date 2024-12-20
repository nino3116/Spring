package com.thehecklers.ch3test;

import org.apache.coyote.Request;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Ch3testApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch3testApplication.class, args);

    }

}
@RestController
@RequestMapping("/member")
class MemberController {
    // 데이터
    private List<Member> members = new ArrayList<>();

    public MemberController() {
        members.addAll(List.of(
                new Member("qwer", "nino", 23, "010-0000-1234", "nino@naver.com", new Date()),
                new Member("qwert", "ninoo", 27, "010-0001-1234", "ninoo@hanmail.net", new Date()),
                new Member("qwerty", "ninooo", 30, "010-0002-1234", "ninooo@hanmail.net", new Date())
        ));
    }

    @GetMapping
    Iterable<Member> getMembers() {
        return members;
    }

    @GetMapping("/{userid}")
    Optional<Member> getMemberUserId(@PathVariable String userid){
        for (Member m : members) {
            if (m.getUserid().equals(userid)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    @PostMapping("/new")
    Member postMember(@RequestBody Member m){
        members.add(m);
        return m;
    }

    @PutMapping("/{userid}")
    public ResponseEntity<Member> updateMember(@PathVariable String userId, @RequestBody Member member) {
        int memberIndex = -1;
        for (Member m : members) {
            if (m.getUserid().equals(userId)) {
                memberIndex = members.indexOf(m);
                members.set(memberIndex, member);
            }
        }
        return (memberIndex == -1)
                ? new ResponseEntity<>(postMember(member), HttpStatus.CREATED)
                : new ResponseEntity<>(member, HttpStatus.OK);

    }

    @DeleteMapping("/{userid}")
    void deleteMember(@PathVariable String userId) {
        members.removeIf(m -> m.getUserid().equals(userId));
    }

}



class Member {
    private String userid;
    private String username;
    private int age;
    private String phone;
    private String email;
    private Date createDate;

    public Member() {}
    public Member(String userid, String username, int age, String phone, String email, Date createDate) {
        this.userid = userid;
        this.username = username;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.createDate = createDate;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
