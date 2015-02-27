package com.brynwyl.letho.util;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.PNGDecoder;

public class ImageInfo {
	public ByteBuffer buf;
	public int width, height, glImageType, imageId;

	public ImageInfo(ByteBuffer buf, int width, int height, int glImageType) {
		this.buf = buf;
		this.width = width;
		this.height = height;
		this.glImageType = glImageType;
	}

	public int registerGlTex() {
		imageId = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, imageId);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
				GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
				GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glImageType, width,
				height, 0, glImageType, GL11.GL_UNSIGNED_BYTE, buf);

		return imageId;
	}

	public int bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, imageId);
		return imageId;
	}

	public static ImageInfo loadTextureResource(String name)
			throws IOException {
		PNGDecoder dec = new PNGDecoder(
				ImageInfo.class.getResourceAsStream(name));
		int width = dec.getWidth();
		int height = dec.getHeight();
		boolean halph = dec.hasAlpha();
		int glType = halph ? GL11.GL_RGBA : GL11.GL_RGB;
		int bpp = halph ? 4 : 3;
		PNGDecoder.Format fmt = halph ? PNGDecoder.RGBA
				: PNGDecoder.RGB;

		ByteBuffer buf = BufferUtils.createByteBuffer(width * height * bpp);
		dec.decode(buf, width * bpp, fmt);
		buf.flip();

		return new ImageInfo(buf, width, height, glType);
	}
}
