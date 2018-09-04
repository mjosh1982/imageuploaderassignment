package com.upgrad.ImageHoster.service;

import com.upgrad.ImageHoster.model.Comment;
import com.upgrad.ImageHoster.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getAll();

    List<Image> getByTag(String tagName);

    Image getById(String id);

    Image getByIdWithJoin(String id);

    Image getById(int id);

    void deleteById(Image image);

    void save(Image image);

    void update(Image image);

    void saveComment(Comment comment);
}