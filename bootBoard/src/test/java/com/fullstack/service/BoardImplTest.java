package com.fullstack.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.fullstack.dto.BoardDTO;
import com.fullstack.dto.PageRequestDTO;
import com.fullstack.dto.PageResultDTO;
import com.fullstack.entity.Board;
import com.fullstack.entity.Member;
import com.fullstack.entity.Reply;
import com.fullstack.repository.BoardRepository;
import com.fullstack.repository.MemberRepository;
import com.fullstack.repository.ReplyRepository;

@SpringBootTest
public class BoardImplTest {
	
	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	// @Test
	public void regiTest()
	{
		Optional<Member> res = memberRepository.findById("user98@aaa.com");
		
		Member member = res.get();
		
		BoardDTO boardDTO = BoardDTO.builder()
				.title("유닛 테스트용 제목")
				.content("유닛 테스트용 내용")
				.writerEmail(member.getEmail())
				.writerName(member.getName())
				.build();
		
		System.out.println(boardService.register(boardDTO));
	}
	
	// 목록 테스트
	// @Test
	public void testList()
	{
		PageRequestDTO pageRequestDTO = new PageRequestDTO();
		
		PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
		
		for(BoardDTO boardDTO : result.getDtoList())
		{
			System.out.println(boardDTO);
		}
	}
	
	// 글 상세 테스트
	// @Test
	public void testGet()
	{
		Long bno = 100L;
		
		BoardDTO boardDTO = boardService.get(bno);
		
		System.out.println(boardDTO);
	}
	
	
	// 글 삭제 테스트
	// @Test
	public void deleteWithReply()
	{
		Long bno = 7L;
		boardService.removeWithReply(bno);
	}
	
	// 글 수정 테스트
	@Commit
	// @Test
	public void testModify()
	{
		Long bno = 50L;
		BoardDTO dto = BoardDTO.builder()
				.bno(bno)
				.title("제목 변경")
				.content("내용 변경")
				.build();
		
		boardService.modify(dto);
	}
	
	//searchboard test
	// @Test
	public void searchTest() {
		//boardRepository.search1();
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		boardRepository.searchpage("t", "1", pageable);
		
	}

	// @Test
	public void testListByBoard()
	{
		List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(90L).build());
		
		System.out.println(replyList);
	}
	
}











