package com.jobinlawrance.pics.data.mock

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by jobinlawrance on 8/9/17.
 */
class MockPhotoResponses {
    companion object {

        private val moshi = Moshi.Builder().build()
        private val type = Types.newParameterizedType(List::class.java, PhotoResponse::class.java)
        private val adapter = moshi.adapter<List<PhotoResponse>>(type)

        @JvmStatic
        val jsonString = "[\n" +
                "   {\n" +
                "      \"id\":\"nkHBFwVBzkg\",\n" +
                "      \"created_at\":\"2017-09-08T03:23:21-04:00\",\n" +
                "      \"updated_at\":\"2017-09-08T12:52:40-04:00\",\n" +
                "      \"width\":3648,\n" +
                "      \"height\":4665,\n" +
                "      \"color\":\"#1E1C0B\",\n" +
                "      \"likes\":12,\n" +
                "      \"liked_by_user\":false,\n" +
                "      \"description\":null,\n" +
                "      \"user\":{\n" +
                "         \"id\":\"2vwna2AUGzo\",\n" +
                "         \"updated_at\":\"2017-09-08T12:54:31-04:00\",\n" +
                "         \"username\":\"cravethebenefits\",\n" +
                "         \"name\":\"Brenda Godinez\",\n" +
                "         \"first_name\":\"Brenda\",\n" +
                "         \"last_name\":\"Godinez\",\n" +
                "         \"twitter_username\":\"cravethebenefts\",\n" +
                "         \"portfolio_url\":\"http://cravethebenefits.com/\",\n" +
                "         \"bio\":\"Food Nerd. Breakfast Lover. Freelance writer, food photographer, & health blogger. \",\n" +
                "         \"location\":\"Costa Rica\",\n" +
                "         \"total_likes\":1,\n" +
                "         \"total_photos\":19,\n" +
                "         \"total_collections\":0,\n" +
                "         \"profile_image\":{\n" +
                "            \"small\":\"https://images.unsplash.com/profile-1490323661174-682bfb6ce36d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=4992cb3a8cc5be91a9d4546e4218f8a0\",\n" +
                "            \"medium\":\"https://images.unsplash.com/profile-1490323661174-682bfb6ce36d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=eb16bc0a30290331d3e0e563405e0516\",\n" +
                "            \"large\":\"https://images.unsplash.com/profile-1490323661174-682bfb6ce36d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=66ecae7d864a7a40c9585a74435ffdbc\"\n" +
                "         },\n" +
                "         \"links\":{\n" +
                "            \"self\":\"https://api.unsplash.com/users/cravethebenefits\",\n" +
                "            \"html\":\"https://unsplash.com/@cravethebenefits\",\n" +
                "            \"photos\":\"https://api.unsplash.com/users/cravethebenefits/photos\",\n" +
                "            \"likes\":\"https://api.unsplash.com/users/cravethebenefits/likes\",\n" +
                "            \"portfolio\":\"https://api.unsplash.com/users/cravethebenefits/portfolio\",\n" +
                "            \"following\":\"https://api.unsplash.com/users/cravethebenefits/following\",\n" +
                "            \"followers\":\"https://api.unsplash.com/users/cravethebenefits/followers\"\n" +
                "         }\n" +
                "      },\n" +
                "      \"current_user_collections\":[\n" +
                "\n" +
                "      ],\n" +
                "      \"urls\":{\n" +
                "         \"raw\":\"https://images.unsplash.com/photo-1504855328839-2f4baf9e0fd7\",\n" +
                "         \"full\":\"https://images.unsplash.com/photo-1504855328839-2f4baf9e0fd7?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=852cbb7062fbf96d9be6dde044d13376\",\n" +
                "         \"regular\":\"https://images.unsplash.com/photo-1504855328839-2f4baf9e0fd7?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=dd3e99075565de86a504168ea03640d0\",\n" +
                "         \"small\":\"https://images.unsplash.com/photo-1504855328839-2f4baf9e0fd7?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=c20c87ffd5427736338057bce7519d83\",\n" +
                "         \"thumb\":\"https://images.unsplash.com/photo-1504855328839-2f4baf9e0fd7?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=6847a6e58764f66c03dd7de0624584be\"\n" +
                "      },\n" +
                "      \"categories\":[\n" +
                "\n" +
                "      ],\n" +
                "      \"links\":{\n" +
                "         \"self\":\"https://api.unsplash.com/photos/nkHBFwVBzkg\",\n" +
                "         \"html\":\"https://unsplash.com/photos/nkHBFwVBzkg\",\n" +
                "         \"download\":\"https://unsplash.com/photos/nkHBFwVBzkg/download\",\n" +
                "         \"download_location\":\"https://api.unsplash.com/photos/nkHBFwVBzkg/download\"\n" +
                "      }\n" +
                "   },\n" +
                "   {\n" +
                "      \"id\":\"AHBvAIVqk64\",\n" +
                "      \"created_at\":\"2017-09-07T21:42:26-04:00\",\n" +
                "      \"updated_at\":\"2017-09-08T13:05:52-04:00\",\n" +
                "      \"width\":2506,\n" +
                "      \"height\":3133,\n" +
                "      \"color\":\"#070C0F\",\n" +
                "      \"likes\":8,\n" +
                "      \"liked_by_user\":false,\n" +
                "      \"description\":null,\n" +
                "      \"user\":{\n" +
                "         \"id\":\"HX6V-jVHGJY\",\n" +
                "         \"updated_at\":\"2017-09-08T13:05:52-04:00\",\n" +
                "         \"username\":\"oladimeg\",\n" +
                "         \"name\":\"Oladimeji Odunsi\",\n" +
                "         \"first_name\":\"Oladimeji\",\n" +
                "         \"last_name\":\"Odunsi\",\n" +
                "         \"twitter_username\":\"Oladimeg\",\n" +
                "         \"portfolio_url\":\"http://www.oladimeg.com\",\n" +
                "         \"bio\":\"Photographer / Content Creator,\\r\\nInstagram: @Oladimeg\",\n" +
                "         \"location\":\"Canada\",\n" +
                "         \"total_likes\":0,\n" +
                "         \"total_photos\":9,\n" +
                "         \"total_collections\":0,\n" +
                "         \"profile_image\":{\n" +
                "            \"small\":\"https://images.unsplash.com/profile-fb-1493517384-ca6f7b435c76.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=b80cce6148dfe5410cd7cee4e0399fa1\",\n" +
                "            \"medium\":\"https://images.unsplash.com/profile-fb-1493517384-ca6f7b435c76.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=9736f6dacd36272b15f6f34a134fe874\",\n" +
                "            \"large\":\"https://images.unsplash.com/profile-fb-1493517384-ca6f7b435c76.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=dc64724d54791a29810e757890f4622e\"\n" +
                "         },\n" +
                "         \"links\":{\n" +
                "            \"self\":\"https://api.unsplash.com/users/oladimeg\",\n" +
                "            \"html\":\"https://unsplash.com/@oladimeg\",\n" +
                "            \"photos\":\"https://api.unsplash.com/users/oladimeg/photos\",\n" +
                "            \"likes\":\"https://api.unsplash.com/users/oladimeg/likes\",\n" +
                "            \"portfolio\":\"https://api.unsplash.com/users/oladimeg/portfolio\",\n" +
                "            \"following\":\"https://api.unsplash.com/users/oladimeg/following\",\n" +
                "            \"followers\":\"https://api.unsplash.com/users/oladimeg/followers\"\n" +
                "         }\n" +
                "      },\n" +
                "      \"current_user_collections\":[\n" +
                "\n" +
                "      ],\n" +
                "      \"urls\":{\n" +
                "         \"raw\":\"https://images.unsplash.com/photo-1504834636679-cd3acd047c06\",\n" +
                "         \"full\":\"https://images.unsplash.com/photo-1504834636679-cd3acd047c06?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=160914e3becd79159bad68ec5dfdeeea\",\n" +
                "         \"regular\":\"https://images.unsplash.com/photo-1504834636679-cd3acd047c06?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=0bea099f9fd9fb1a235a7dc8a1bb6955\",\n" +
                "         \"small\":\"https://images.unsplash.com/photo-1504834636679-cd3acd047c06?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=a05eebeaf8e71eb008dc7fed510c2847\",\n" +
                "         \"thumb\":\"https://images.unsplash.com/photo-1504834636679-cd3acd047c06?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=bbbdeb0e0fc22bf8d2bf3203e7eedf05\"\n" +
                "      },\n" +
                "      \"categories\":[\n" +
                "\n" +
                "      ],\n" +
                "      \"links\":{\n" +
                "         \"self\":\"https://api.unsplash.com/photos/AHBvAIVqk64\",\n" +
                "         \"html\":\"https://unsplash.com/photos/AHBvAIVqk64\",\n" +
                "         \"download\":\"https://unsplash.com/photos/AHBvAIVqk64/download\",\n" +
                "         \"download_location\":\"https://api.unsplash.com/photos/AHBvAIVqk64/download\"\n" +
                "      }\n" +
                "   },\n" +
                "   {\n" +
                "      \"id\":\"ABtmE3jhaPQ\",\n" +
                "      \"created_at\":\"2017-09-07T20:18:13-04:00\",\n" +
                "      \"updated_at\":\"2017-09-08T13:07:51-04:00\",\n" +
                "      \"width\":8270,\n" +
                "      \"height\":5514,\n" +
                "      \"color\":\"#282727\",\n" +
                "      \"likes\":34,\n" +
                "      \"liked_by_user\":false,\n" +
                "      \"description\":null,\n" +
                "      \"user\":{\n" +
                "         \"id\":\"GGr86YKpU9I\",\n" +
                "         \"updated_at\":\"2017-09-08T13:07:51-04:00\",\n" +
                "         \"username\":\"norrisniman\",\n" +
                "         \"name\":\"Norbert von Niman\",\n" +
                "         \"first_name\":\"Norbert\",\n" +
                "         \"last_name\":\"von Niman\",\n" +
                "         \"twitter_username\":null,\n" +
                "         \"portfolio_url\":null,\n" +
                "         \"bio\":\"Photographer and Adventure Guide\",\n" +
                "         \"location\":\"Iceland\",\n" +
                "         \"total_likes\":0,\n" +
                "         \"total_photos\":4,\n" +
                "         \"total_collections\":0,\n" +
                "         \"profile_image\":{\n" +
                "            \"small\":\"https://images.unsplash.com/profile-1503924054425-b4861b5bbff3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=57253e0b3bb7ddf967762a3cebb7e8a2\",\n" +
                "            \"medium\":\"https://images.unsplash.com/profile-1503924054425-b4861b5bbff3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=7d99a82b99408fbfe4e813abf4c49141\",\n" +
                "            \"large\":\"https://images.unsplash.com/profile-1503924054425-b4861b5bbff3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=49b06493abf9c6d10b702ed67349319f\"\n" +
                "         },\n" +
                "         \"links\":{\n" +
                "            \"self\":\"https://api.unsplash.com/users/norrisniman\",\n" +
                "            \"html\":\"https://unsplash.com/@norrisniman\",\n" +
                "            \"photos\":\"https://api.unsplash.com/users/norrisniman/photos\",\n" +
                "            \"likes\":\"https://api.unsplash.com/users/norrisniman/likes\",\n" +
                "            \"portfolio\":\"https://api.unsplash.com/users/norrisniman/portfolio\",\n" +
                "            \"following\":\"https://api.unsplash.com/users/norrisniman/following\",\n" +
                "            \"followers\":\"https://api.unsplash.com/users/norrisniman/followers\"\n" +
                "         }\n" +
                "      },\n" +
                "      \"current_user_collections\":[\n" +
                "\n" +
                "      ],\n" +
                "      \"urls\":{\n" +
                "         \"raw\":\"https://images.unsplash.com/photo-1504829857797-ddff29c27927\",\n" +
                "         \"full\":\"https://images.unsplash.com/photo-1504829857797-ddff29c27927?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=ea2cd942b7735bfe43ed4464750896df\",\n" +
                "         \"regular\":\"https://images.unsplash.com/photo-1504829857797-ddff29c27927?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=199caf146223bbc26fb974795044dac8\",\n" +
                "         \"small\":\"https://images.unsplash.com/photo-1504829857797-ddff29c27927?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=aa149bd730b0ae5b83c82c7fca16161f\",\n" +
                "         \"thumb\":\"https://images.unsplash.com/photo-1504829857797-ddff29c27927?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=075e1d79d12f587beaf09ecb2a07ab59\"\n" +
                "      },\n" +
                "      \"categories\":[\n" +
                "\n" +
                "      ],\n" +
                "      \"links\":{\n" +
                "         \"self\":\"https://api.unsplash.com/photos/ABtmE3jhaPQ\",\n" +
                "         \"html\":\"https://unsplash.com/photos/ABtmE3jhaPQ\",\n" +
                "         \"download\":\"https://unsplash.com/photos/ABtmE3jhaPQ/download\",\n" +
                "         \"download_location\":\"https://api.unsplash.com/photos/ABtmE3jhaPQ/download\"\n" +
                "      }\n" +
                "   }\n" +
                "]"

        @JvmStatic
        fun asList() = adapter.fromJson(jsonString)
    }
}