package com.supermaket.GMarket.service;

import com.supermaket.GMarket.DTO.WalletDTO;
import com.supermaket.GMarket.entity.User;
import com.supermaket.GMarket.entity.Wallet;
import com.supermaket.GMarket.exceptions.BadRequestException;
import com.supermaket.GMarket.exceptions.NotFoundException;
import com.supermaket.GMarket.mapper.WalletMapper;
import com.supermaket.GMarket.repository.UserRepository;
import com.supermaket.GMarket.repository.WalletRepository;
import com.supermaket.GMarket.request.WalletRequest;
import com.supermaket.GMarket.responses.BaseBodyResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    public BaseBodyResponse<List<WalletDTO>> getAll() {
        List<Wallet> wallets = walletRepository.findAll();
        if (wallets.isEmpty()) {
            throw new BadRequestException("A lista de carteiras está vazia");
        }
        return WalletMapper.toListResponse(wallets);
    }

    @Transactional
    public BaseBodyResponse<WalletDTO> create(@Valid WalletRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException("Usuário com o id " + request.getUserId() + " não existe");
        }
        Wallet newWallet = walletRepository.save(WalletMapper.toWallet(request));
        if (user.isPresent()) {
            newWallet.addUser(user.get());
        }
        return WalletMapper.toResponse(newWallet);
    }

    public BaseBodyResponse<WalletDTO> getById(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet != null) {
            return WalletMapper.toResponse(wallet);
        } else {
            throw new NotFoundException("Carteira não encontrada com o ID: " + walletId);
        }
    }

}
