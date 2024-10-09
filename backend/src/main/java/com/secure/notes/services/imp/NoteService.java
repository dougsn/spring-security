package com.secure.notes.services.imp;

import com.secure.notes.models.Note;
import com.secure.notes.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements com.secure.notes.services.NoteService {

    @Autowired
    private NoteRepository repository;

    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        return repository.save(note);
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = repository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found."));
        note.setContent(content);
        return repository.save(note);
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        if (repository.findById(noteId).isPresent()) {
            repository.deleteById(noteId);
        } else {
            throw new RuntimeException("This note not found.");
        }
    }

    @Override
    public List<Note> getNotesForUser(String username) {
        return repository.findByOwnerUsername(username);
    }
}
