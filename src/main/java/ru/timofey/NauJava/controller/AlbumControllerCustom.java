package ru.timofey.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.timofey.NauJava.entity.Album;
import ru.timofey.NauJava.repository.AlbumRepository;

@Controller
@RequestMapping("/api/albums/custom/view")
public class AlbumControllerCustom {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumControllerCustom(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping("/list")
    public String userListView(Model model)
    {
        Iterable<Album> albums = albumRepository.findAll();
        model.addAttribute("albums", albums);
        return "album/albums";
    }
}
