package rafael.ordonez.facebook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rafael.ordonez.facebook.model.operations.PhotosAlbum;

/**
 * Created by rafa on 17/8/15.
 */
@Controller
@RequestMapping("/api/facebook")
public class FacebookOperationsController {

    private Facebook facebook;

    @Autowired
    public FacebookOperationsController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource<PhotosAlbum>> getUserPhotos() {
        PhotosAlbum firstAlbum = getFirstAlbum();
        firstAlbum.addPhotos(facebook.mediaOperations().getPhotos(firstAlbum.getId()));

        ResponseEntity<Resource<PhotosAlbum>> photosResponseEntity = new ResponseEntity<>(new Resource(firstAlbum), HttpStatus.OK);
        return photosResponseEntity;
    }

    private PhotosAlbum getFirstAlbum() {
        PhotosAlbum album = null;
        PagedList<Album> albums = facebook.mediaOperations().getAlbums();
        if (!albums.isEmpty()) {
            album = new PhotosAlbum(albums.get(0).getId(), albums.get(0).getName(), albums.get(0).getDescription());
        }
        return album;
    }

}
