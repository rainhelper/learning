package net.rainhelper.learning.controller;

import com.kakao.hanover.api.controller.dto.MusicRoomAlbumSimpleDto;
import com.kakao.hanover.api.controller.dto.paging.FriendMusicRoomPagingStrategy;
import com.kakao.hanover.api.controller.dto.paging.PaginationDto;
import com.kakao.hanover.api.controller.dto.paging.PagingStrategy;
import com.kakao.hanover.domain.*;
import com.kakao.hanover.dto.BgmTrackDto;
import com.kakao.hanover.i14y.kapi.FriendsApiClient;
import com.kakao.hanover.repository.BgmTrackRepository;
import com.kakao.hanover.repository.MemberFollowerRepository;
import com.kakao.hanover.repository.MemberRepository;
import com.kakao.hanover.repository.MusicRoomAlbumRepository;
import com.kakao.hanover.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajkuhn on 15. 8. 16..
 */
@RestController
@RequestMapping("/test")
@Api(value= "api test", description = "api 테스트용")
public class TestController extends AbstractApiController {

    @Autowired
    MemberService memberService;

    @Autowired
    MusicRoomAlbumRepository musicRoomAlbumRepository;

    @Autowired
    BgmTrackRepository bgmTrackRepository;

    @Autowired
    FriendsApiClient friendsApiClient;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberFollowerRepository memberFollowerRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> test() {
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");

        logger.debug("TTTTTTTTTT");
        logger.error("Ttttttttttttt", new Throwable("xxx"));

        return list;
    }

    @ApiOperation(value="카카오 친구 뮤직룸 목록 가져오기, request parameter로 nextPageKey 값을 통해 페이징 데이터요청. 없을경우 첫페이지 제공")
    @RequestMapping(value="registeredFriend", method = RequestMethod.GET)
    public PaginationDto getFriendsMusicRoomList(HttpServletRequest request, @RequestParam(required = false, value = "nextPageKey") Integer nextPageKey) throws IOException {

        // TODO request.getAttribute("i14yIdType").toString() 리팩토링, enum type으로 변경
        List<MemberActivity> memberActivityList = memberService.getRecentMemberActivityList(
                getFriendIdList(request),
                "KAKAO"
        );

        PagingStrategy pagingStrategy = new FriendMusicRoomPagingStrategy(nextPageKey, memberActivityList.size());
        memberActivityList = getPaginationList(memberActivityList, pagingStrategy);

        //get (musicroom's defaultMraId list) from (memberActivity list)
        return new PaginationDto(pagingStrategy.getNextPageKey(), getMusicRoomAlbumSimpleDtoList(memberActivityList));
    }

    @ApiOperation(value="팔로우 친구 뮤직룸 목록")
    @RequestMapping(value="followingMember", method = RequestMethod.GET)
    public PaginationDto getFollowingMemberMusicRoomList(@RequestParam(required = false, value = "nextPageKey") Integer nextPageKey) throws IOException {
        Member member = memberRepository.findOne(34L);
        List<MemberFollower> memberFollowerListForFollowingMemberList = member.getFollowingList();
        List<Member> followingMemberList = new ArrayList<>();
        for(MemberFollower memberFollower : memberFollowerListForFollowingMemberList) {
            followingMemberList.add(memberFollower.getMember());
        }
        List<MemberActivity> memberActivityList = memberService.getFollowFriendActivityList(followingMemberList);
        PagingStrategy pagingStrategy = new FriendMusicRoomPagingStrategy(nextPageKey, memberActivityList.size());
        memberActivityList = getPaginationList(memberActivityList, pagingStrategy);

        return new PaginationDto(pagingStrategy.getNextPageKey(), getMusicRoomAlbumSimpleDtoList(memberActivityList));
    }

    private List<MemberActivity> getPaginationList(List<MemberActivity> memberActivityList, PagingStrategy pagingStrategy) {
        return memberActivityList.subList(pagingStrategy.getStartElementIndex(), pagingStrategy.getLastElementIndex() + 1);
    }

    private List<MusicRoomAlbumSimpleDto> getMusicRoomAlbumSimpleDtoList(List<MemberActivity> memberActivityList) {

        List<MusicRoomAlbumSimpleDto> simpleDtoList = new ArrayList<>();
        for (MemberActivity memberActivity : memberActivityList) {
            MusicRoomAlbumSimpleDto simpleDto = getMusicRoomAlbumSimpleDto(memberActivity);
            simpleDtoList.add(simpleDto);
        }

        return simpleDtoList;
    }

    private MusicRoomAlbumSimpleDto getMusicRoomAlbumSimpleDto(MemberActivity memberActivity) {
        MusicRoomAlbumSimpleDto musicRoomAlbumSimpleDto = new MusicRoomAlbumSimpleDto();

        Member member = memberActivity.getMember();
        MusicRoom musicRoom = member.getMusicRoom();
        MusicRoomAlbum musicRoomAlbum = musicRoomAlbumRepository.findByMraId(musicRoom.getDefaultMraId());

        musicRoomAlbumSimpleDto.setMemberData(member);
        musicRoomAlbumSimpleDto.setMemberActivityData(memberActivity);
        musicRoomAlbumSimpleDto.setMusicRoomData(musicRoom);
        musicRoomAlbumSimpleDto.setMusicRoomAlbumData(musicRoomAlbum);

        Long trackId = musicRoomAlbum.getFirstBgmTrackId();

        if (trackId != null) {
            BgmTrackDto bgmTrackDto = new BgmTrackDto();
            bgmTrackDto.set(bgmTrackRepository.findOne(trackId));

            musicRoomAlbumSimpleDto.setTitleBgmTrack(bgmTrackDto);
        }

        return musicRoomAlbumSimpleDto;
    }

    private List<String> getFriendIdList(HttpServletRequest request) throws IOException {
        /**
         * test data
         *
         * 30 - 55355902
         * 31 - 57437284
         * 33 - 55342124
         * 34 - 6560766
         */

        List<String> friendIdList = new ArrayList<>();
        friendIdList.add("55355902");
        friendIdList.add("57437284");
        friendIdList.add("55342124");
        friendIdList.add("6560766");

        return friendIdList;
    }
}
