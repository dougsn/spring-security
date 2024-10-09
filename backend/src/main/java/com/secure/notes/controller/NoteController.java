package com.secure.notes.controller;

import com.secure.notes.models.Note;
import com.secure.notes.services.imp.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@Slf4j
public class NoteController {
    @Autowired
    private NoteService service;

    @PostMapping
    public Note createNote(@RequestBody String content,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("USER DETAILS: {}", username);
        return service.createNoteForUser(username, content);
    }

    @GetMapping
    public List<Note> getUserNotes(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("USER DETALS: {}", username);
        return service.getNotesForUser(username);
    }

    @PutMapping("/{noteId}")
    public Note updatedNote(@PathVariable Long noteId,
                            @RequestBody String content,
                            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return service.updateNoteForUser(noteId, content, username);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        service.deleteNoteForUser(noteId, username);
    }

}
