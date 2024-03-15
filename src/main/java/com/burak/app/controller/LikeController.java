package com.burak.app.controller;


import com.burak.app.requests.LikeCreateRequest;
import com.burak.app.responses.LikeResponse;
import com.burak.app.services.ILikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final ILikeService likeService;

    public LikeController(ILikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getLikesByForeignKeys(@RequestParam(required = false) Long userId,
                                                    @RequestParam(required = false) Long postId){
        return likeService.getLikesByForeignKeys(userId, postId);
    }

    @GetMapping("/{likeId}")
    public LikeResponse getLikeById(@PathVariable Long likeId){
        return new LikeResponse(likeService.getLikeById(likeId));
    }

    @PostMapping
    public ResponseEntity<Void> createLike(@RequestBody LikeCreateRequest likeCreateRequest){
        return (likeService.createLike(likeCreateRequest) != null) ? new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
    }
}
