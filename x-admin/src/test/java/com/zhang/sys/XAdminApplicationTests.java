package com.zhang.sys;


import com.zhang.sys.entity.Comment;
import com.zhang.sys.entity.User;
import com.zhang.sys.mapper.CommentMapper;
import com.zhang.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class XAdminApplicationTests {

//	@Resource
//	private UserMapper userMapper;
//
//	@Test
//	void testMapper(){
//		List<User> users = userMapper.selectList(null);
//		users.forEach(System.out::println);
//	}


	@Resource
	private CommentMapper commentMapper;
	@Test
	void testCommentMapper(){
		List<Comment> ret = commentMapper.getAllByNoteIDCommentList(763207694);
		for(int i = 0;i < ret.size();++i){
			System.out.println(ret.get(i));
		}
		System.out.println("total has " + ret.size() + " element!");
	}


}
