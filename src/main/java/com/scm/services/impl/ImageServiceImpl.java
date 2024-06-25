package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helpers.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	private Cloudinary cloudinary;

	public ImageServiceImpl(Cloudinary cloudinary) {
		super();
		this.cloudinary = cloudinary;
	}

	@Override
	public String uploadImage(MultipartFile picture, String fileName) {

		

		try {
			byte[] data = new byte[picture.getInputStream().available()];

			picture.getInputStream().read(data);

			cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));

			return this.getUrlFromPublicId(fileName);

		} catch (IOException e) {

			e.printStackTrace();

			return null;
		}

	}

	@Override
	public String getUrlFromPublicId(String publicId) {

		return cloudinary.url()
				.transformation(new Transformation<>().width(AppConstants.PICTURE_IMAGE_WIDTH)
						.height(AppConstants.PICTURE_IMAGE_HEIGHT).crop(AppConstants.PICTURE_IMAGE_CROP))
				.generate(publicId);
	}

}
