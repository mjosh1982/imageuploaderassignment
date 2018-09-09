package com.upgrad.ImageHoster.common;

import com.google.common.collect.Lists;
import com.upgrad.ImageHoster.model.Comment;
import com.upgrad.ImageHoster.model.Image;
import org.hibernate.*;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ImageManager extends SessionManager {
    /**
     * This method retrieves all of the images saved in the database
     *
     * @return a List of Image objects
     */
    public List<Image> getAllImages() {
        Session session = openSession();
        List<Image> images = session.createCriteria(Image.class)
                //.setProjection(Projections.projectionList()
                //         .add(Projections.distinct(Projections.property("id"))))
                .list();
        commitSession(session);
        Set<Image> imageSet = new HashSet<>(images);
        return Lists.newArrayList(imageSet);
    }

    /**
     * This method retrieves an image by its title
     *
     * @param id the title of the image that we are looking for
     * @return an Image object that we retrieved by its title
     */
    public Image getImageById(final String id) {
        Session session = openSession();

        try {
            Image image = (Image) session.createCriteria(Image.class)
                    .add(Restrictions.eq("id", Integer.valueOf(id)))
                    .uniqueResult(); // retrieves only 1 image
            commitSession(session);
            return image;
        } catch (HibernateException e) {
            System.out.println("unable to retrieve an image from database by its title");
        }

        return null;
    }

    /**
     * This method retrieves an image by its title, as well as the data
     * related to its tags, user, and user's profile photo
     *
     * @param id the title of the image that we are looking for
     * @return an Image object that we retrieved by its title
     */
    public Image getImageByIdWithJoins(final String id) {
        Session session = openSession();

        try {
            Image image = (Image) session.createCriteria(Image.class)
                    .add(Restrictions.eq("id", Integer.valueOf(id)))
                    .uniqueResult();
            Hibernate.initialize(image.getTags()); // doing a join on tags table
            Hibernate.initialize(image.getUser()); // doing a join on user table
            Hibernate.initialize(image.getUser().getProfilePhoto()); // doing a join on profile photo table
            commitSession(session);

            return image;
        } catch (HibernateException e) {
            System.out.println("unable to retrieve an image from database by its title");
        }

        return null;
    }


    /**
     * This method retrieves an image by a specific tag.
     *
     * @param tagName the tag that we want to retrieve images by
     * @return a list of Image objects that we retrieved by its tag
     */
    public List<Image> getImagesByTag(final String tagName) {
        Session session = openSession();

        try {
            List<Image> images = session
                    .createCriteria(Image.class)
                    .createAlias("tags", "tags")
                    .add(Restrictions.eq("tags.name", tagName))
                    .list();

            commitSession(session);

            return images;
        } catch (HibernateException e) {
            System.out.println("unable to retrieve an image from database by its title");
        }

        return null;
    }

    /**
     * This method retrieves the number of images stored in the database
     *
     * @return the number of images stored in the database
     */
    public long getNumberOfImages() {
        Session session = openSession();

        // to learn more about Hibernate Projection:
        // https://stackoverflow.com/questions/7498205/when-to-use-hibernate-projections
        Long numImages = (Long) session.createCriteria(Image.class).setProjection(Projections.rowCount()).uniqueResult();
        commitSession(session);

        return numImages;
    }

    /**
     * This method deletes an image data from the database
     *
     * @param id the title of the image that we want to delete
     */
    public void deleteImage(final String id) {
        Session session = openSession();
        Query query = session.createQuery("Delete from " + Image.class.getName() + " where id=:id");
        query.setParameter("id", Integer.valueOf(id));
        query.executeUpdate();
        commitSession(session);
    }

    /**
     * This method saves an image's data to the database
     *
     * @param image the Image who's data that we want to save to the database
     */
    public void saveImage(final Image image) {
        Session session = openSession();
        session.save(image);
        commitSession(session);
    }

    /**
     * This method updates an image's data in the database
     *
     * @param updatedImage an Image object with the updated data
     */
    public void updateImage(final Image updatedImage) {
        Session session = openSession();
        session.update(updatedImage);
        commitSession(session);
    }

    /**
     * This method retrieves an image by its primary key id field
     *
     * @param id the title of the image that we are looking for
     * @return an Image object that we retrieved by its title
     */
    public Image getImageById(final int id) {
        Session session = openSession();

        try {
            Image image = (Image) session.createCriteria(Image.class)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult(); // retrieves only 1 image
            commitSession(session);

            return image;
        } catch (HibernateException e) {
            System.out.println("unable to retrieve an image from database by its title");
        }

        return null;
    }

    /**
     * This method is used for saving comments for the image
     *
     * @param comment comment parameter
     */
    public void saveComment(Comment comment) {
        Session session = openSession();
        session.save(comment);
        commitSession(session);
    }

    /**
     * Method used for deleting comments.
     *
     * @param comments list of comments for the image
     */
    public void deleteComments(Set<Comment> comments) {
        for (Comment comObj : comments) {
            Session session = openSession();
            session.delete(comObj);
            commitSession(session);
        }
    }
}