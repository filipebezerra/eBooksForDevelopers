package io.github.filipebezerra.ebooksfordevelopers.model;

public class RecyclerModel {
	
	private Long mId;
    private String mText;
    private String mImage;

    public RecyclerModel(Long id, String text, String image) {
       	mId = id;
		mText = text;
        mImage = image;
    }

	public void setId(Long id) {
		mId = id;
	}

	public Long getId() {
		return mId;
	}

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }
	
}
