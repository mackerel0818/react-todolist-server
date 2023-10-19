package com.example.todo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.MemoDTO;
import com.example.todo.dto.ResponseDTO;
import com.example.todo.model.MemoEntity;
import com.example.todo.service.MemoService;

@RestController
@RequestMapping("memos")
public class MemoController {
    @Autowired
    private MemoService memoService;

    @PostMapping
    public ResponseEntity<?> createMemo(@AuthenticationPrincipal String userId, @RequestBody MemoEntity entity) {
        try {
            List<MemoEntity> entities = memoService.createMemo(entity, userId);
            List<MemoDTO> dtos = entities.stream().map(MemoDTO::new).collect(Collectors.toList());
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveMemos(@AuthenticationPrincipal String userId) {
        List<MemoEntity> entities = memoService.retrieveMemos(userId);
        List<MemoDTO> dtos = entities.stream().map(MemoDTO::new).collect(Collectors.toList());
        ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<?> retrieveMemosByCategory(@AuthenticationPrincipal String userId, @PathVariable String categoryId) {
        List<MemoEntity> entities = memoService.retrieveMemosByCategory(userId, categoryId);
        List<MemoDTO> dtos = entities.stream().map(MemoDTO::new).collect(Collectors.toList());
        ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateMemo(@AuthenticationPrincipal String userId, @RequestBody MemoEntity entity) {
        try {
            List<MemoEntity> entities = memoService.updateMemo(entity, userId);
            List<MemoDTO> dtos = entities.stream().map(MemoDTO::new).collect(Collectors.toList());
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/all")
    public ResponseEntity<?> updateMemoDefaultCategory(@AuthenticationPrincipal String userId, @RequestBody MemoEntity entity) {
        try {
            List<MemoEntity> entities = memoService.updateMemoDefaultCategory(entity, userId);
            List<MemoDTO> dtos = entities.stream().map(MemoDTO::new).collect(Collectors.toList());
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMemo(@AuthenticationPrincipal String userId, @RequestBody MemoEntity entity) {
        try {
            List<MemoEntity> entities = memoService.deleteMemo(entity, userId);
            List<MemoDTO> dtos = entities.stream().map(MemoDTO::new).collect(Collectors.toList());
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<MemoDTO> response = ResponseDTO.<MemoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
