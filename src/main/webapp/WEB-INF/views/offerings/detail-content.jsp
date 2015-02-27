<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="offering-detail" class="row container-fluid">

  <div class="col-smartphone-10 col-tablet-5 col-desktop-3">
    <div class="panel panel-default">

      <div class="panel-heading">
        <div class="thumbnail">
          <img src="${offering.imageUrl}">
        </div>
        <div class="panel-title">${ offering.displayName }</div>
      </div>

      <div class="panel-body">
        <div class="list-options">
          <button class="list-element btn btn-success">
            <span class=" fa fa-download"></span>
            <span class="text-plain">Download</span>
          </button>  
        </div>
      </div><!-- /.panel -->

    </div>
  </div><!-- /.col-* -->

  <div class="col-smartphone-10 col-tablet-5 col-desktop-7">
    <div class="panel panel-default">

      <div class="panel-heading">
        <div class="panel-title">Offer Details</div>
      </div><!-- /.panel-heading -->

      <div class="panel-body">
        <dl class="dl-vertical">
          <dt>Description</dt>
          <dd class="text-justify">${ offering.description }</dd>
        </dl>
        <div class="title-descriptions">Additional Information</div>
        <dl class="dl-vertical">
          <dt>Current Version</dt>
          <dd>${ offering.version }</dd>
          <dt>Developer</dt>
          <dd>${ offering.describedIn.creator.displayName }</dd>
          <dt>Upload Date</dt>
          <dd><fmt:formatDate pattern="yyyy-MM-dd" value="${offering.describedIn.registrationDate}" /></dd>
        </dl>
        <div class="title-descriptions">Store Information</div>
        <dl class="dl-vertical">
          <dt>Name</dt>
          <dd>${ offering.store.displayName }</dd>
          <dt>Creator</dt>
          <dd>${ offering.store.creator.displayName }</dd>
          <dt>Registration Date</dt>
          <dd><fmt:formatDate pattern="yyyy-MM-dd" value="${offering.store.registrationDate}" /></dd>
        </dl>
      </div><!-- /.panel-body -->

    </div><!-- /.panel -->
  </div><!-- /.col-* -->

</div><!-- /.row -->