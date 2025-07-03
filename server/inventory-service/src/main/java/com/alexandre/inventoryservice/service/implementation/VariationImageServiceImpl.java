package com.alexandre.inventoryservice.service.implementation;

import com.alexandre.inventoryservice.dto.VariationImageDTO;
import com.alexandre.inventoryservice.entity.VariationImage;
import com.alexandre.inventoryservice.exception.NotFoundException;
import com.alexandre.inventoryservice.mapper.VariationImageMapper;
import com.alexandre.inventoryservice.repository.VariationImageRepository;
import com.alexandre.inventoryservice.service.VariationImageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VariationImageServiceImpl implements VariationImageService {

    private final VariationImageRepository variationImageRepository;
    private final VariationImageMapper variationImageMapper;

    @Override
    @Transactional(readOnly = true)
    public VariationImageDTO findById(UUID id) {
        return variationImageRepository
                .findById(id)
                .map(variationImageMapper::toDto)
                .orElseThrow(() -> new NotFoundException("VariationImage not found"));
    }

    @Override
    public VariationImageDTO create(VariationImageDTO variationImageDTO) {
        VariationImage variationImage = variationImageMapper.toEntity(variationImageDTO);
        return variationImageMapper.toDto(variationImageRepository.save(variationImage));
    }

    /**
     * Note here that we can't change the variation linked to an image
     * @param variationImageDTO the new variation image data, it's required to have the id included
     * @return the new updated variation image
     */
    @Override
    public VariationImageDTO update(VariationImageDTO variationImageDTO) {
        VariationImage oldVariationImageOptional = variationImageRepository
                .findById(variationImageDTO.getId())
                .orElseThrow(() -> new NotFoundException("VariationImage not found"));


        oldVariationImageOptional.setUri(variationImageDTO.getUri());
        oldVariationImageOptional.setThumbnail(variationImageDTO.getThumbnail());
        oldVariationImageOptional.setOrderIndex(variationImageDTO.getOrderIndex());

        variationImageRepository.save(oldVariationImageOptional);
        return variationImageMapper.toDto(oldVariationImageOptional);
    }

    @Override
    public void deleteById(UUID uuid) {
        variationImageRepository.deleteById(uuid);
    }
}
