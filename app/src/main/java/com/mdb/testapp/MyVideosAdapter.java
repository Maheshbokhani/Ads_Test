package com.mdb.testapp;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allattentionhere.autoplayvideos.AAH_CustomViewHolder;
import com.allattentionhere.autoplayvideos.AAH_VideosAdapter;
import com.squareup.picasso.Picasso;
import java.util.List;

import butterknife.ButterKnife;

public class MyVideosAdapter extends AAH_VideosAdapter {

   List<MyModel> list;
   Picasso picasso;
    private static final int TYPE_VIDEO = 0, TYPE_TEXT = 1;


    public class MyViewHolder extends AAH_CustomViewHolder {

         TextView tv;
         ImageView img_vol, img_playback;
        //to mute/un-mute video (optional)
        boolean isMuted;

        public MyViewHolder(View x) {
            super(x);
            tv = ButterKnife.findById(x, R.id.tv);
            img_vol = ButterKnife.findById(x, R.id.img_vol);
            img_playback = ButterKnife.findById(x, R.id.img_playback);
        }

        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.pause);
            if (isMuted) {
                muteVideo();
                img_vol.setImageResource(R.drawable.play);
            } else {
                unmuteVideo();
                img_vol.setImageResource(R.drawable.firstlogo);
            }
        }


        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.play);
        }

    }

        public class MyTextViewHolder extends AAH_CustomViewHolder {
            final TextView tv;

            public MyTextViewHolder(View x) {
                super(x);
                tv = ButterKnife.findById(x, R.id.tv);
            }
        }

        public MyVideosAdapter(List<MyModel> list_urls, Picasso p) {
            this.list = list_urls;
            this.picasso = p;
        }


        @Override
        public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType==TYPE_TEXT) {
                View itemView1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_text, parent, false);
                return new MyTextViewHolder(itemView1);
            } else {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_card, parent, false);
                return new MyViewHolder(itemView);
            }

        }

        @Override
        public void onBindViewHolder(final AAH_CustomViewHolder holder, int position) {
            if (list.get(position).getName().startsWith("text")) {
                ((MyTextViewHolder) holder).tv.setText(list.get(position).getName());
            } else {
                ((MyViewHolder) holder).tv.setText(list.get(position).getName());

                //todo
                holder.setImageUrl(list.get(position).getImage_url());
                holder.setVideoUrl(list.get(position).getVideo_url());

                //load image into imageview
                if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty()) {
                    picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());
                }

                holder.setLooping(true); //optional - true by default

                //to play pause videos manually (optional)
                ((MyViewHolder) holder).img_playback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.isPlaying()) {
                            holder.pauseVideo();
                            holder.setPaused(true);
                        } else {
                            holder.playVideo();
                            holder.setPaused(false);
                        }
                    }
                });

                //to mute/un-mute video (optional)
                ((MyViewHolder) holder).img_vol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((MyViewHolder) holder).isMuted) {
                            holder.unmuteVideo();
                            ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.pause);
                        } else {
                            holder.muteVideo();
                            ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.play);
                        }
                        ((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
                    }
                });

                if (list.get(position).getVideo_url() == null) {
                    ((MyViewHolder) holder).img_vol.setVisibility(View.GONE);
                    ((MyViewHolder) holder).img_playback.setVisibility(View.GONE);
                } else {
                    ((MyViewHolder) holder).img_vol.setVisibility(View.VISIBLE);
                    ((MyViewHolder) holder).img_playback.setVisibility(View.VISIBLE);
                }
            }
        }


        @Override
        public int getItemCount() {
            return list.size();
        }


        @Override
        public int getItemViewType(int position) {
            if (list.get(position).getName().startsWith("text")) {
                return TYPE_TEXT;
            } else return TYPE_VIDEO;
        }






}
