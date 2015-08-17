package rafael.ordonez.facebook.model.operations;

import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Photo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 17/8/15.
 */
public class PhotosAlbum implements Serializable {

    private static final long serialVersionUID = 6067752286505721519L;

    private String name;
    private String description;
    private String id;
    public List<String> photos;

    public PhotosAlbum(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addPhotos(PagedList<Photo> facebookPhotos) {
        for (Photo photo : facebookPhotos) {
            photos.add(photo.getName());
        }
    }

    public String getId() {
        return id;
    }
}
