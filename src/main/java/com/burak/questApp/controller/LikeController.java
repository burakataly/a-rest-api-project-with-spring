package com.burak.questApp.controller;


import com.burak.questApp.entities.Like;
import com.burak.questApp.requests.LikeCreateRequest;
import com.burak.questApp.responses.LikeResponse;
import com.burak.questApp.services.ILikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private ILikeService likeService;

    public LikeController(ILikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getLikesByForeignKeys(@RequestParam(required = false) Long userId,
                                                    @RequestParam(required = false) Long postId){
        return likeService.getLikesByForeignKeys(userId, postId);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId){
        return likeService.getLikeById(likeId);
    }

    @PostMapping
    public Like createLike(@RequestBody LikeCreateRequest likeCreateRequest){
        return likeService.createLike(likeCreateRequest);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
    }
}
