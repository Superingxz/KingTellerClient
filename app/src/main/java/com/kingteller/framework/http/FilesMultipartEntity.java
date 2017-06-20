package com.kingteller.framework.http;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;

/**
 * 多文件上传 上传进度 与AjaxFilesParams配合
 * 
 * @author 王定波
 * 
 */
@SuppressWarnings("deprecation")
public class FilesMultipartEntity extends
		org.apache.http.entity.mime.MultipartEntity {

	private final ProgressListener listener;

	public FilesMultipartEntity(final ProgressListener listener) {
		super();
		this.listener = listener;
	}

	public FilesMultipartEntity(final HttpMultipartMode mode,
			final ProgressListener listener) {
		super(mode);
		this.listener = listener;
	}

	public FilesMultipartEntity(HttpMultipartMode mode, final String boundary,
			final Charset charset, final ProgressListener listener) {
		super(mode, boundary, charset);
		this.listener = listener;
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		if (this.listener != null)
			super.writeTo(new CountingOutputStream(outstream, this.listener,
					getContentLength()));
		else
			super.writeTo(outstream);
	}

	public static interface ProgressListener {
		void onProgess(long total, long num);
	}

	public static class CountingOutputStream extends FilterOutputStream {

		private final ProgressListener listener;
		private long transferred;
		private long total;

		public CountingOutputStream(final OutputStream out,
				final ProgressListener listener, long total) {
			super(out);
			this.listener = listener;
			this.transferred = 0;
			this.total = total;
		}

		public void write(byte[] b, int off, int len) throws IOException {
			out.write(b, off, len);
			this.transferred += len;
			this.listener.onProgess(total, this.transferred);
		}

		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			this.listener.onProgess(total, this.transferred);
		}
	}
}
