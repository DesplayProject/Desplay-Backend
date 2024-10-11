package com.deterior.domain.board.controller

import com.deterior.domain.board.dto.BoardWriteDto
import com.deterior.domain.board.dto.BoardWriteRequest
import com.deterior.domain.board.facade.BoardFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/api")
class BoardController @Autowired constructor(
    val boardFacade: BoardFacade
) {

}