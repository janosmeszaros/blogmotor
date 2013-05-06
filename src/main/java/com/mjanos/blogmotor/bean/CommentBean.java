package com.mjanos.blogmotor.bean;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Comment;
import com.mjanos.blogmotor.model.Post;

/**
 * Bean to manipulate {@link Comment}s.
 * @author Janos_Gyula_Meszaros
 */
@Scope("session")
@Component("commentBean")
public class CommentBean {
    private static final Logger LOG = LoggerFactory.getLogger(CommentBean.class);

    @Autowired
    @Qualifier("commentGenericDAO")
    private GenericDAO<Comment> commentDAO;

    @Autowired
    private UserBean userBean;
    @Autowired
    private PostBean postBean;

    private Comment newComment;
    private Post actualPost;
    private Comment commentToEdit;

    /**
     * Save new comment to the post.
     */
    public void saveNewComment() {
        addPostToNewMessage();
        addOwnerToNewMessage();
        addDateToNewComment();
        persistNewComment();
        invalidateComment();
    }

    private void invalidateComment() {
        newComment = null;
    }

    private void persistNewComment() {
        actualPost = postBean.getActual();
        actualPost.getComments().add(newComment);
        commentDAO.persist(newComment);
        postBean.update(actualPost);
    }

    private void addDateToNewComment() {
        newComment.setCommentDate(new Date());
    }

    private void addOwnerToNewMessage() {
        newComment.setOwner(userBean.getLoggedInUser());
    }

    private void addPostToNewMessage() {
        newComment.setPost(actualPost);
    }

    /**
     * Delete the comment with the id.
     * @param id
     *            {@link Comment}'s id.
     */
    public void delete(final long id) {
        LOG.debug("Delete comment with id: " + id);
        final Comment commentToDelete = commentDAO.getById(id);
        commentDAO.delete(commentToDelete);
        postBean.showActual(postBean.getActual().getId());
    }

    /**
     * Edit the {@link Comment} with the specified id.
     * @param id
     *            {@link Comment}'s id.
     */
    public void edit(final long id) {
        commentToEdit = commentDAO.getById(id);
        LOG.debug("Edit comment: " + commentToEdit.getComment() + " id: " + commentToEdit.getId());
    }

    /**
     * Save the edited comment.
     */
    public void saveEditedComment() {
        commentDAO.update(commentToEdit);
        refreshPost();
    }

    private void refreshPost() {
        postBean.getActualFromDB(postBean.getActual().getId());
    }

    /**
     * Invalidate the edit comment.
     */
    public void invalidateEditComment() {
        commentToEdit = null;
    }

    /**
     * Get new comment.
     * @return {@link Comment}
     */
    public Comment getNewComment() {
        if (newComment == null) {
            newComment = new Comment();
        }
        return newComment;
    }

    public void setNewComment(final Comment newComment) {
        this.newComment = newComment;
    }

    public Comment getCommentToEdit() {
        return commentToEdit;
    }

    public void setCommentToEdit(final Comment commentToEdit) {
        this.commentToEdit = commentToEdit;
    }

}
