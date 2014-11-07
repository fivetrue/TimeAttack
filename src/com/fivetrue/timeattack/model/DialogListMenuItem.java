package com.fivetrue.timeattack.model;

public class DialogListMenuItem {
	private String name = null;
	private android.view.View.OnClickListener onClickListener = null;

	public DialogListMenuItem(String name, android.view.View.OnClickListener onClick){
		this.name = name;
		this.onClickListener = onClick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public android.view.View.OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
}
