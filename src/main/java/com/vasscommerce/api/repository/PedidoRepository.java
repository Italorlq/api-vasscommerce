package com.vasscommerce.api.repository;

import com.vasscommerce.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteIdOrderByDataCadastroDesc(Long clienteId);
}
