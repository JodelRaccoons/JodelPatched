package com.jodelapp.jodelandroidv3.features.feed;

import android.widget.TextView;

import com.jodelapp.jodelandroidv3.api.model.Post;

import java.util.List;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * ? For distance formatting
 */
@SuppressWarnings({"ConstantConditions", "unused", "InfiniteRecursion"})
@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class FeedRecyclerAdapter {

    @SuppressWarnings("WeakerAccess")
    @DexIgnore
    static class PostViewHolder {
        @DexIgnore
        TextView cornerText;
    }

    @DexIgnore
    private List<Post> getPosts() {
        return null;
    }

    @DexWrap
    private void bindPostViewHolder(PostViewHolder postViewHolder, int i) {
        try {
            bindPostViewHolder(postViewHolder, i);
            List<Post> mPosts = getPosts();
            if (mPosts != null && mPosts.get(i) != null) {
                Post mPost = getPosts().get(i);
                if (!mPost.isFromHome()) {
                    if (postViewHolder != null && postViewHolder.cornerText != null) {
                        postViewHolder.cornerText.setText(String.valueOf(mPost.getDistance()) + " km");
                    }
                }
            }
        } catch(IndexOutOfBoundsException e) {
            /*IGNORED*/
        }
    }
}
