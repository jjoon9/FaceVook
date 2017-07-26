package layout;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joon.facevook.Article;
import com.example.joon.facevook.ArticleLab;
import com.example.joon.facevook.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import it.sephiroth.android.library.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleListFragment extends Fragment {

    private RecyclerView mArticleRecyclerView;
    private ArticleAdapter mAdapter;



    //    private ProgressDialog progressDialog;
    Bitmap bitmap;
    String urlString;
    private Bitmap getImageFromUrl(String u){
        urlString = u;
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        try{
            mThread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return bitmap;
    }


    public ArticleListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        mArticleRecyclerView = (RecyclerView) view.findViewById(R.id.article_recycler_view);
        mArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        ArticleLab articleLab = ArticleLab.get(getActivity());
        List<Article> articles = articleLab.getArticles();
        mAdapter = new ArticleAdapter(articles);
        mArticleRecyclerView.setAdapter(mAdapter);
    }



    public static ArticleListFragment newInstance(){
//        Bundle args = new Bundle();
        ArticleListFragment fragment = new ArticleListFragment();
//        fragment.setArguments(args);
        fragment.setArguments(new Bundle());
        return fragment;
    }


    private class ArticleHeaderHolder extends RecyclerView.ViewHolder{


        public ArticleHeaderHolder(View itemView){
            super(itemView);
//            mWriter = (TextView) itemView.findViewById(R.id.writer);
        }


        public void bindArticle(Article article){

        }

    }

    private class ArticleHolder extends RecyclerView.ViewHolder{

        private Article mArticle;

        private TextView mWriter;
        private TextView mTime;
        private ImageView mProfileImage;
        private TextView mContentText;
        private ImageView mContentImage;
        private TextView mInfoLeft;
        private TextView mInfoRight;
        private TextView mLikeButton;
        private TextView mCommentButton;
        private TextView mShareButton;

        public ArticleHolder(View itemView){
            super(itemView);

            mWriter = (TextView) itemView.findViewById(R.id.writer);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mProfileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            mContentText = (TextView) itemView.findViewById(R.id.content_text);
            mContentImage= (ImageView) itemView.findViewById(R.id.content_image);
            mInfoLeft = (TextView) itemView.findViewById(R.id.info_left);
            mInfoRight = (TextView) itemView.findViewById(R.id.info_right);
            mLikeButton = (TextView) itemView.findViewById(R.id.like_button);
            mCommentButton = (TextView) itemView.findViewById(R.id.comment_button);
            mShareButton = (TextView) itemView.findViewById(R.id.share_button);

        }


        public void bindArticle(Article article){
            mArticle = article;
            mWriter.setText(mArticle.getWriter());
            mTime.setText(mArticle.getTime());
//            mProfileImage.setImageBitmap(getImageFromUrl(mArticle.getProfileImage()));
            Picasso.with(getActivity())
                    .load(mArticle.getProfileImage())
                    .placeholder(R.drawable.dummy_profile)
                    .into(mProfileImage);
            mContentText.setText(mArticle.getContentText());
//            mContentImage.setImageBitmap(getImageFromUrl(mArticle.getContentImage()));
            Picasso.with(getActivity())
                    .load(mArticle.getContentImage())
                    .into(mContentImage);
            mInfoLeft.setText(mArticle.getInfoLeft());
            mInfoRight.setText(mArticle.getInfoRight());
            mLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "좋아연!", Toast.LENGTH_SHORT).show();
                    ArticleLab articleLab = ArticleLab.get(getActivity());
                    articleLab.setArticleLikes(mArticle.getId(),+1);
//                    mAdapter.notifyItemChanged(getAdapterPosition());//@@내부클래스에서도 밖에 클래스의 멤버변수 참조 가능하네?(mAdapter)
                    mInfoLeft.setText(mArticle.getInfoLeft());
                    //@@엥 왜되지?? 콜바이벨류아닌가??@@

                }
            });
            mCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "댓끌!", Toast.LENGTH_SHORT).show();
                }
            });
            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "0U!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Article> mArticles;
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        public ArticleAdapter(List<Article> articles){
            mArticles = articles;
        }

        @Override
        public int getItemCount(){
//            if (mArticles.isEmpty())
//                return 0;
//            else
                return mArticles.size()+1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position==0)
                return TYPE_HEADER;
            else
                return TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity()); //@@기억잘해.
            View view;
            switch (viewType){
                case TYPE_ITEM:
                    view = layoutInflater.inflate(R.layout.article, parent, false);
                    return new ArticleHolder(view);
                case TYPE_HEADER:
                    view = layoutInflater.inflate(R.layout.article_header, parent, false);
                    return new ArticleHeaderHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ArticleHolder) {
                Article article = mArticles.get(position-1);
                ArticleHolder itemHolder = (ArticleHolder) holder;
                itemHolder.bindArticle(article);
            }

        }
    }
}