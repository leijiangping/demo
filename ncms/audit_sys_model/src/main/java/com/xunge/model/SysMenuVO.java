package com.xunge.model;

import java.io.Serializable;

/**
 * 顶层菜单结点VO
 * @author SongJ
 *
 */
public class SysMenuVO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3841317905916266919L;

	private String menuId;

    private String menuCode;

    private String menuName;

    private String menuUrl;

    private Integer menuIcon;

    private String menuNote;

    private String pmenuId;

    private Integer menuOrder;

    private Integer menuState;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuNote() {
        return menuNote;
    }

    public void setMenuNote(String menuNote) {
        this.menuNote = menuNote == null ? null : menuNote.trim();
    }

    public Integer getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(Integer menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getPmenuId() {
        return pmenuId;
    }

    public void setPmenuId(String pmenuId) {
        this.pmenuId = pmenuId == null ? null : pmenuId.trim();
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }
}