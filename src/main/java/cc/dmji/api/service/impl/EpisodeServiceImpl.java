package cc.dmji.api.service.impl;

import cc.dmji.api.entity.Bangumi;
import cc.dmji.api.entity.Episode;
import cc.dmji.api.entity.Video;
import cc.dmji.api.repository.EpisodeRepository;
import cc.dmji.api.service.EpisodeService;
import cc.dmji.api.utils.DmjiUtils;
import cc.dmji.api.utils.EpisodePageInfo;
import cc.dmji.api.utils.PageInfo;
import cc.dmji.api.web.model.VideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.List;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Override
    public EpisodePageInfo listEpisodes() {
        return this.listEpisodes(1,20);
    }

    @Override
    public EpisodePageInfo listEpisodes(Integer pageNum) {
        return this.listEpisodes(pageNum,20);
    }

    @Override
    public EpisodePageInfo listEpisodes(Integer pageNum, Integer pageSize) {
        Page<Episode> result = episodeRepository.findAll(PageRequest.of(pageNum-1,pageSize));
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,result.getTotalElements());
        return new EpisodePageInfo(result.getContent(),pageInfo);
    }

    @Override
    public EpisodePageInfo listEpisodesByBangumiId(Long bangumiId) {
        return this.listEpisodesByBangumiId(bangumiId,1,20);
    }

    @Override
    public EpisodePageInfo listEpisodesByBangumiId(Long bangumiId, int pn) {
        return this.listEpisodesByBangumiId(bangumiId,pn,20);
    }

    @Override
    public EpisodePageInfo listEpisodesByBangumiId(Long bangumiId, int pn, int ps) {
        Page<Episode> result = null;
        result = episodeRepository.findEpisodesByBangumiIdEquals(bangumiId,
                PageRequest.of(pn-1,ps,Sort.Direction.ASC,"epIndex"));
        PageInfo pageInfo = new PageInfo(pn,ps,result.getTotalElements());
        return new EpisodePageInfo(result.getContent(),pageInfo);
    }

    @Override
    public List<Episode> listAllEpisodesByBangumiId(Long bangumiId) {
        return episodeRepository.findEpisodesByBangumiIdEquals(bangumiId);
    }

    @Override
    public List<Episode> listEpisodesByEpIds(List<Long> epIds) {
        return episodeRepository.findAllById(epIds);
    }


    @Override
    public Episode getEpisodeByBangumiIdAndEpIndex(Long bangumiId, Integer epIndex) {
        return episodeRepository.findEpisodeByBangumiIdEqualsAndEpIndexEquals(bangumiId,epIndex);
    }

    @Override
    public Episode getEpisodeByEpId(Long epId) {
        return episodeRepository.findById(epId).orElse(null);
    }

    @Override
    public Episode insertEpisode(Episode episode) {
        setCreateAndModifyTime(episode);
        episode.setDanmakuId(DmjiUtils.getUUID32());
        episode.setViewCount(0L);
        return episodeRepository.save(episode);
    }

    @Override
    public List<Episode> insertEpisodes(List<Episode> episodes) {
        episodes.forEach(e->{
            setCreateAndModifyTime(e);
            e.setDanmakuId(DmjiUtils.getUUID32());
            e.setViewCount(0L);
        });
        return episodeRepository.saveAll(episodes);
    }

    @Override
    public Episode updateEpisode(Episode episode) {
        setModifyTime(episode);
        return episodeRepository.save(episode);
    }

    @Override
    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }

    @Override
    public void deleteEpisodes(List<Episode> episodes) {
        episodeRepository.deleteInBatch(episodes);
    }

    @Override
    public long countEpisode() {
        return episodeRepository.count();
    }

    @Override
    public long countEpisodeByBangumiId(Long bangumiId){

        return episodeRepository.countEpisodesByBangumiIdEquals(bangumiId);
    }

    @Override
    public Page<Episode> listEpisodeByViewCount(Integer pn, Integer ps) {
        return episodeRepository.findAll(PageRequest.of(pn-1,ps,Sort.Direction.DESC,"viewCount"));
    }

    private void setModifyTime(Episode episode){
        Timestamp date = new Timestamp(System.currentTimeMillis());
        episode.setModifyTime(date);
    }

    private void setCreateAndModifyTime(Episode episode){
        Timestamp date = new Timestamp(System.currentTimeMillis());
        episode.setModifyTime(date);
        episode.setCreateTime(date);
    }
}
