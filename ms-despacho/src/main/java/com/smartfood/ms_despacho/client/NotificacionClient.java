package com.smartfood.ms_despacho.client;

import com.smartfood.ms_despacho.dto.NotificacionRequestExternalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-notificaciones", url = "${ms.notificaciones.url}")
public interface NotificacionClient {
    @PostMapping
    void enviar(@RequestBody NotificacionRequestExternalDTO dto);
}