package com.fullstack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fullstack.entity.Board;
import com.fullstack.entity.Member;
import com.fullstack.entity.Reply;
import com.fullstack.repository.BoardRepository;
import com.fullstack.repository.MemberRepository;
import com.fullstack.repository.ReplyRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class BootBoardApplicationTests {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	// @Test
	void contextLoads() {
		IntStream.rangeClosed(1, 100)
		.forEach(i -> {
			Member member = Member.builder()
					.email("user" + i + "@aaa.com")
					.password("1111")
					.name("사용자" + i)
					.build();
			
			memberRepository.save(member);
		});
		
	}
	
	// @Test
	void boardInsert()
	{
		IntStream.rangeClosed(1, 100)
		.forEach(i -> {
			Member member = Member.builder().email("user"+ i + "@aaa.com").build();
			
			Board board = Board.builder()
					.title("제목" + i)
					.content("내용" + i)
					.writer(member)
					.build();
			
			boardRepository.save(board);
		});
	}
	
	// @Test
	void ReplyInsert()
	{
		// 랜덤한 board 번호 생성 후, 밀어 넣기
		IntStream.rangeClosed(1, 100).forEach(i -> {
			long bno = (long)(Math.random() * 100) + 1;
			
			Board board = Board.builder().bno(bno).build();
			
			Reply reply = Reply.builder()
					.text("댓글" + i)
					.board(board)							
					.replyer("guest")
					.build();
			
			replyRepository.save(reply);
							
		});
	}
	
	// board 테이블의 select 를 통해, join 이 걸린 테이블까지 조회하는 내용 확인
	// @Transactional
	// @Test
	public void testBoard()
	{
		Optional<Board> result = boardRepository.findById(100L);
		
		Board board = result.get();
		
		System.out.println(board);
		System.out.println(board.getWriter());
	}
	
	// @Test
	public void testRepl()
	{
		Optional<Reply> res = replyRepository.findById(21L);
		
		Reply reply = res.get();
		
		System.out.println(reply);
		System.out.println(reply.getBoard());
	}
	
	// @Test
	public void testReadWithWriter()
	{
		Object res = boardRepository.getBoardWithWriter(100L);
		
		Object[] arr = (Object[])res;
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println(Arrays.toString(arr));
		
	}
	
	@Transactional
	// @Test
	public void testReadWithReply()
	{
		List<Object[]> res = boardRepository.getBoardWithReply(35L);
		
		for(Object[] arr : res)
		{
			System.out.println("------------------------------------------------------------------------");
			System.out.println(Arrays.toString(arr));
		}
	}
	
	// @Test
	public void testWithReplyCount()
	{
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		
		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
		
		result.get().forEach(row ->{
			Object[] arr = (Object[])row;
			
			System.out.println(Arrays.toString(arr));
		});
	}

	// @Test
	public void testWithBoardBno()
	{
		Object res = boardRepository.getBoardByBno(100L);
		
		Object[] arr = (Object[])res;
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println(Arrays.toString(arr));
	}
	
	
}














