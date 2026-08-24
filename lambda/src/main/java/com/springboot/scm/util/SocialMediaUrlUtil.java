package com.springboot.scm.util;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialMediaUrlUtil {

    private SocialMediaUrlUtil() {
    }

    private static final Pattern YOUTUBE_SHORT =
            Pattern.compile("youtube\\.com/shorts/([^?&/]+)");

    private static final Pattern YOUTUBE_WATCH =
            Pattern.compile("[?&]v=([^&]+)");

    private static final Pattern YOUTUBE_EMBED =
            Pattern.compile("youtube\\.com/embed/([^?&/]+)");

    private static final Pattern YOUTUBE_LIVE =
            Pattern.compile("youtube\\.com/live/([^?&/]+)");

    private static final Pattern YOUTU_BE =
            Pattern.compile("youtu\\.be/([^?&/]+)");

    public static String extractYoutubeVideoId(String url){

        if(url==null || url.isBlank()){
            return null;
        }

        Matcher matcher;

        matcher = YOUTUBE_SHORT.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        }

        matcher = YOUTUBE_WATCH.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        }

        matcher = YOUTUBE_EMBED.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        }

        matcher = YOUTUBE_LIVE.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        }

        matcher = YOUTU_BE.matcher(url);
        if(matcher.find()){
            return matcher.group(1);
        }

        return null;
    }

    public static String youtubeEmbedUrl(String url){

        String id = extractYoutubeVideoId(url);

        if(id==null){
            return null;
        }

        return "https://www.youtube.com/embed/" + id;
    }

    public static String youtubeThumbnail(String url){

        String id = extractYoutubeVideoId(url);

        if(id==null){
            return null;
        }

        return "https://img.youtube.com/vi/" + id + "/maxresdefault.jpg";
    }

}